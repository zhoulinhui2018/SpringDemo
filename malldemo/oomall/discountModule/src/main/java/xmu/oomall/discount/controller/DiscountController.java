package xmu.oomall.discount.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import xmu.oomall.discount.controller.vo.GrouponRuleVo;
import xmu.oomall.discount.dao.GroupOnDao;
import xmu.oomall.discount.domain.*;
import xmu.oomall.discount.domain.Promotion.PresaleRule;
import xmu.oomall.discount.service.Impl.CouponServiceImpl;
import xmu.oomall.discount.service.Impl.GroupOnRuleService;
import xmu.oomall.discount.service.Impl.PresaleServiceImpl;
import xmu.oomall.discount.util.LogUtil;
import xmu.oomall.discount.util.ResponseUtil;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("")
public class DiscountController {
    @Autowired
    private GroupOnRuleService groupOnRuleService;

    @Autowired
    private CouponServiceImpl couponService;

    @Autowired
    private PresaleServiceImpl presaleService;

    @Autowired
    private GroupOnDao groupOnDao;

    /**
    * @Description: 每天晚上12点处理团购
    * @Param: []
    * @return: java.lang.Object
    * @Author: Zhou Linhui
    * @Date: 2019/12/17
    */
    @Scheduled(cron = "0 0 0 * * ?")
    public Object executeAllGroupon(){
        List<GrouponRulePo> finishedGrouponRules = groupOnRuleService.findFinishedGrouponRules();
        for (GrouponRulePo finishedGrouponRule : finishedGrouponRules) {
            List<Order> grouponOrders = groupOnRuleService.getGrouponOrders(finishedGrouponRule);
            GrouponRuleStrategy accessStrategy = groupOnRuleService.getAccessStrategy(finishedGrouponRule);
            BigDecimal rate = accessStrategy.getRate();
            List<Payment> payments =new ArrayList<>();
            for (int i = 0; i < grouponOrders.size(); i++) {
                Order order =  grouponOrders.get(i);
                List<OrderItem> orderItemList = order.getOrderItemList();
                OrderItem orderItem = orderItemList.get(0);
                BigDecimal price = orderItem.getPrice();
                Integer number1 = orderItem.getNumber();
                BigDecimal number=new BigDecimal(number1);
                BigDecimal dealPrice = price.multiply(number).multiply(rate).setScale(2,BigDecimal.ROUND_FLOOR);
                orderItem.setDealPrice(dealPrice);
                Payment payment=new Payment();
                payment.setActualPrice(dealPrice.subtract(price.multiply(number)));
                payment.setOrderId(order.getId());
                payments.add(payment);
            }
            groupOnRuleService.refund(payments);
//            groupOnRuleService.putOrdersBack(grouponOrders);
        }
        System.out.println("test");
        return null;
    }

    /**
     * @Description: 管理员新增团购规则
     * @Param: [grouponRulePo]
     * @return: java.lang.Object
     * @Author: Zhou Linhui
     * @Date: 2019/12/11
     */
    @PostMapping("/grouponRules")
    public Object create(@RequestBody GrouponRulePo grouponRulePo){
        groupOnRuleService.add(grouponRulePo);
        return ResponseUtil.ok(grouponRulePo);
    }

    /**
     * @Description: 管理员查看某个团购规则详情
     * @Param: [id]
     * @return: java.lang.Object
     * @Author: Zhou Linhui
     * @Date: 2019/12/11
     */
    @GetMapping("/grouponRules/{id}")
    public Object detail(@PathVariable Integer id){
        GrouponRuleVo grouponRuleVo= new GrouponRuleVo();
        GrouponRulePo grouponRulePo = groupOnRuleService.findById(id);
        GoodsPo grouponGoods = groupOnRuleService.getGrouponGoods(grouponRulePo);
        grouponRuleVo.setGoodsPo(grouponGoods);
        grouponRuleVo.setGrouponRulePo(grouponRulePo);
        return ResponseUtil.ok(grouponRuleVo);
    }

    /**
     * @Description: 管理员修改团购信息
     * @Param: [id, grouponRulePo]
     * @return: java.lang.Object
     * @Author: Zhou Linhui
     * @Date: 2019/12/7
     */
    @PutMapping("/grouponRules/{id}")
    public Object update(HttpServletRequest request, @PathVariable Integer id, @RequestBody GrouponRulePo grouponRulePo){
        String adminid= request.getHeader("id");
        if (adminid==null){
            return ResponseUtil.unlogin();
        }
        Boolean inTime = false;
//            int isSuccess= groupOnRuleService.update(grouponRulePo);
        grouponRulePo.setId(id);
        GrouponRulePo grouponRulePo1 = groupOnRuleService.findById(id);
        LocalDateTime now = LocalDateTime.now();
        if (grouponRulePo1.getStartTime().isBefore(now)&&grouponRulePo1.getEndTime().isAfter(now)){
            inTime=true;
        }
        Boolean statusCode = grouponRulePo1.getStatusCode();

        Log log = LogUtil.newLog("修改团购", id, Integer.valueOf(adminid), 2, request.getRemoteAddr());
        if(inTime==true && statusCode==true) {
            List<Order> grouponOrders = groupOnRuleService.getGrouponOrders(grouponRulePo);
            List<Payment> payments =new ArrayList<>();
            for (int i = 0; i < grouponOrders.size(); i++) {
                Order order =  grouponOrders.get(i);
                List<OrderItem> orderItemList = order.getOrderItemList();
                OrderItem orderItem = orderItemList.get(0);
                BigDecimal price = orderItem.getPrice();
                Integer number1 = orderItem.getNumber();
                BigDecimal number=new BigDecimal(number1);
                BigDecimal dealPrice = price.multiply(number).setScale(2,BigDecimal.ROUND_FLOOR);
                orderItem.setDealPrice(dealPrice);
                Payment payment=new Payment();
                payment.setActualPrice(dealPrice.subtract(price.multiply(number)));
                payment.setOrderId(order.getId());
                payments.add(payment);
            }groupOnRuleService.refund(payments);
        }else if(inTime==false){
            //预售未开始或者已经结束可以修改信息
            if (groupOnRuleService.update(grouponRulePo) == 0) {
                groupOnRuleService.log(log);
                return ResponseUtil.updatedDataFailed();
            }
            log.setStatusCode(1);
            groupOnRuleService.log(log);
            return ResponseUtil.ok(grouponRulePo);
        }else{
            //在预售开始到结束时间内且未作废的情况，不能改动信息
            return ResponseUtil.updatedDataFailed();
        }
        grouponRulePo.setId(id);
        groupOnRuleService.update(grouponRulePo);
        return ResponseUtil.ok(grouponRulePo);
    }


    /**
     * @Description: 管理员删除团购
     * @Param: [id, grouponRulePo]
     * @return: java.lang.Object
     * @Author: Zhou Linhui
     * @Date: 2019/12/7
     */
    @DeleteMapping("/grouponRules/{id}")
    public Object delete(@PathVariable Integer id){
        GrouponRulePo grouponRulePo=new GrouponRulePo();
        grouponRulePo.setId(id);
        groupOnRuleService.delete(grouponRulePo);
        return ResponseUtil.ok();
    }

    /**
     * @Description: 用户查看团购商品
     * @Param: [id, groupOnRule]
     * @return: java.lang.Object
     * @Author: Zhou Linhui
     * @Date: 2019/12/7
     */
    @GetMapping("/grouponGoods")
    public Object list(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit) {

        List<GrouponRulePo> rulesList = groupOnRuleService.findGrouponRulePos(page,limit);
        List<GrouponRuleVo> grouponRuleVoList=new ArrayList<>();
        for (GrouponRulePo grouponRulePo : rulesList) {
            GoodsPo grouponGoods = groupOnRuleService.getGrouponGoods(grouponRulePo);
            GrouponRuleVo grouponRuleVo=new GrouponRuleVo();
            grouponRuleVo.setGrouponRulePo(grouponRulePo);
            grouponRuleVo.setGoodsPo(grouponGoods);
            grouponRuleVoList.add(grouponRuleVo);
        }
        return ResponseUtil.ok(grouponRuleVoList);
    }

    @GetMapping("/admin/grouponGoods")
    public Object adminList(@RequestParam(defaultValue = "1") Integer page,
                            @RequestParam(defaultValue = "10") Integer limit){
        List<GrouponRulePo> rulesList = groupOnRuleService.adminFindGrouponRulePos(page,limit);
        List<GrouponRuleVo> grouponRuleVoList=new ArrayList<>();
        for (GrouponRulePo grouponRulePo : rulesList) {
            GoodsPo grouponGoods = groupOnRuleService.getGrouponGoods(grouponRulePo);
            GrouponRuleVo grouponRuleVo=new GrouponRuleVo();
            grouponRuleVo.setGrouponRulePo(grouponRulePo);
            grouponRuleVo.setGoodsPo(grouponGoods);
            grouponRuleVoList.add(grouponRuleVo);
        }
        return ResponseUtil.ok(grouponRuleVoList);
    }

    @PostMapping("/discount/orders")
    public Object discountOrder(Order order){
        Integer couponId=order.getCouponId();
        if(couponId!=0){
            //使用优惠券的普通订单
            List<OrderItem> oldOrderItems=order.getOrderItemList();
            List<OrderItem> newOrderItems=couponService.calcDiscount(oldOrderItems,couponId);
            //修改订单中的明细
            order.setOrderItemList(newOrderItems);
            //使用优惠券的List<Payment>为空
            order.setPaymentList(null);
            for(OrderItem item:newOrderItems){
                //都是普通商品
                item.setItemType(0);
            }
            order.setOrderItemList(newOrderItems);
        }
        else{
            Integer orderItemSize=order.getOrderItemList().size();
            List<OrderItem> orderItemsList=order.getOrderItemList();
            if(orderItemSize!=1){
                //预售订单或是团购订单明细都只能为1,不为1的话说明是没选优惠券的普通订单
                for(OrderItem item:orderItemsList){
                    //都是普通商品
                    item.setItemType(0);
                }
                order.setOrderItemList(orderItemsList);
            }
            else{
                OrderItem item=order.getOrderItemList().get(0);
                Integer goodsId=item.getGoodsId();
                //使用goodsId去预售规则和团购规则中查找
                List<OrderItem> orderItemList1=new ArrayList<>();

                //是团购订单,itemType设置成2
                if(groupOnRuleService.isGrouponOrder(goodsId)==true){

                    item.setItemType(2);
                    List<Payment> payments=groupOnRuleService.getGrouponPayment(order);
                    order.setPaymentList(payments);
                    orderItemList1.add(item);
                    order.setOrderItemList(orderItemList1);
                }
                else {
                    //判断是否是预售订单
                   PresaleRule rule=presaleService.isPresaleOrder(goodsId);
                   List<OrderItem> orderItemList2=new ArrayList<>();
                   if(rule!=null){
                       item.setItemType(1);
                       List<Payment> payments=presaleService.presalePayment(order,rule);
                       order.setPaymentList(payments);
                       orderItemList2.add(item);
                       order.setOrderItemList(orderItemList2);
                   }
                   else{
                       //明细为1的普通订单
                       List<OrderItem> orderItemList3=new ArrayList<>();
                       item.setItemType(0);
                       orderItemList3.add(item);
                       order.setOrderItemList(orderItemList3);
                   }
                }

            }
        }
        return ResponseUtil.ok(order);
    }

    private Object validate(GrouponRulePo grouponRulePo) {
        LocalDateTime startTime = grouponRulePo.getStartTime();
        LocalDateTime endTime = grouponRulePo.getEndTime();
        String grouponLevelStrategy = grouponRulePo.getGrouponLevelStrategy();
        Integer goodsId=grouponRulePo.getGoodsId();
        if (startTime==null || endTime==null || goodsId==null){
            return ResponseUtil.badArgument();
        }
        if (StringUtils.isEmpty(grouponLevelStrategy)) {
            return ResponseUtil.badArgument();
        }
        return null;
    }

}
