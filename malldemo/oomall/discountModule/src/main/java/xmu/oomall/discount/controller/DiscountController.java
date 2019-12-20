package xmu.oomall.discount.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import xmu.oomall.discount.controller.vo.GrouponRuleVo;
import xmu.oomall.discount.controller.vo.PresaleRuleVo;
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
            Integer num = groupOnRuleService.getGrouponNum(finishedGrouponRule);
            GrouponRuleStrategy accessStrategy = groupOnRuleService.getAccessStrategy(finishedGrouponRule,num);
            BigDecimal rate = accessStrategy.getRate();
            groupOnRuleService.returnBackRate(finishedGrouponRule,rate);
        }
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
    public Object create(HttpServletRequest request,@RequestBody GrouponRulePo grouponRulePo){
        String adminid= request.getHeader("id");
        if (adminid==null){
            return ResponseUtil.unlogin();
        }
        Log log = LogUtil.newLog("插入团购", grouponRulePo.getId(), Integer.valueOf(adminid), 1, request.getRemoteAddr());
        if (groupOnRuleService.canAdd(grouponRulePo)==false){
            log.setStatusCode(1);
            groupOnRuleService.log(log);
            return ResponseUtil.fail(722,"团购规则添加失败");
        }
        Object error = validate(grouponRulePo);
        if (error != null) {
            groupOnRuleService.log(log);
            return ResponseUtil.fail(722,"团购规则添加失败");
        }
        try {
            groupOnRuleService.add(grouponRulePo);
        } catch (Exception e) {
            return ResponseUtil.fail(722,"团购规则添加失败");
        }
        log.setStatusCode(1);
        groupOnRuleService.log(log);
        return ResponseUtil.ok(grouponRulePo);
    }

    /**
     * @Description: 管理员查看某个团购规则详情
     * @Param: [id]
     * @return: java.lang.Object
     * @Author: Zhou Linhui
     * @Date: 2019/12/11
     */
    @GetMapping("/admin/grouponRules/{id}")
    public Object detail(HttpServletRequest request,@PathVariable Integer id){
        String adminid= request.getHeader("id");
        if (adminid==null){
            return ResponseUtil.unlogin();
        }

        Log log = LogUtil.newLog("查看团购详情", id, Integer.valueOf(adminid), 0, request.getRemoteAddr());

        GrouponRuleVo grouponRuleVo= new GrouponRuleVo();

        GrouponRulePo grouponRulePo = null;
        try {
            grouponRulePo = groupOnRuleService.findById(id);
        } catch (Exception e) {
            return ResponseUtil.fail(720,"该团购规则是无效团购规则");
        }
        if (grouponRulePo==null ){
            groupOnRuleService.log(log);
            return ResponseUtil.fail(720,"该团购规则是无效团购规则");
        }

        GoodsPo grouponGoods = groupOnRuleService.getGrouponGoods(grouponRulePo);
        grouponRuleVo.setGoodsPo(grouponGoods);
        grouponRuleVo.setGrouponRulePo(grouponRulePo);
        log.setStatusCode(1);
        groupOnRuleService.log(log);
        return ResponseUtil.ok(grouponRuleVo);
    }

    @PostMapping("/grouponRules/{id}/invalid")
    public Object invalid(HttpServletRequest request,@PathVariable Integer id){
        String adminid= request.getHeader("id");
        if (adminid==null){
            return ResponseUtil.unlogin();
        }
        Log log = LogUtil.newLog("下架团购", id, Integer.valueOf(adminid), 2, request.getRemoteAddr());
        Boolean inTime=false;
        GrouponRulePo grouponRulePo = null;
        try {
            grouponRulePo = groupOnRuleService.findById(id);
        } catch (Exception e) {
            groupOnRuleService.log(log);
            ResponseUtil.fail(721,"团购修改失败");
        }
        if (grouponRulePo==null){
            groupOnRuleService.log(log);
            ResponseUtil.fail(720,"团购修改失败");
        }
        LocalDateTime now = LocalDateTime.now();
        if (grouponRulePo.getStartTime().isBefore(now)&&grouponRulePo.getEndTime().isAfter(now)){
            inTime=true;
        }
        if (inTime == false){
            GrouponRulePo grouponRulePoNew=new GrouponRulePo();
            grouponRulePoNew.setStatusCode(false);
            int update = groupOnRuleService.update(grouponRulePoNew);
            if (update==0){
                return ResponseUtil.fail(721,"团购修改失败");
            }
            log.setStatusCode(1);
            groupOnRuleService.log(log);
            return ResponseUtil.ok();
        }
        groupOnRuleService.returnBackRate(grouponRulePo,new BigDecimal(1));
        log.setStatusCode(1);
        groupOnRuleService.log(log);
        //如果是下架操作的话究竟更不更改其他信息这个点不明确
        return ResponseUtil.ok();
    }

    /**
     * @Description: 管理员修改团购信息   要改
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
        if (id<=0){
            return ResponseUtil.fail();
        }
        Log log = LogUtil.newLog("修改团购", id, Integer.valueOf(adminid), 2, request.getRemoteAddr());
        Boolean inTime = false;
        grouponRulePo.setId(id);

        GrouponRulePo grouponRulePo1 = null;
        try {
            grouponRulePo1 = groupOnRuleService.findById(id);
        } catch (Exception e) {
            groupOnRuleService.log(log);
            return ResponseUtil.fail(721,"团购规则修改失败");
        }
        if (grouponRulePo1==null){
            groupOnRuleService.log(log);
            return ResponseUtil.fail(720,"该团购规则是无效团购规则");
        }
        LocalDateTime now = LocalDateTime.now();
        if (grouponRulePo1.getStartTime().isBefore(now)&&grouponRulePo1.getEndTime().isAfter(now)){
            inTime=true;
        }
        Boolean statusCode = grouponRulePo1.getStatusCode();

        if (inTime==true){
            return ResponseUtil.fail(721,"团购规则修改失败");
        }

        try {
            groupOnRuleService.update(grouponRulePo);
        } catch (Exception e) {
            return ResponseUtil.fail(721,"团购规则修改失败");
        }
        log.setStatusCode(1);
        groupOnRuleService.log(log);
//        if(inTime==true && statusCode==true && grouponRulePo.getStatusCode()==false) {
//            groupOnRuleService.returnBackRate(grouponRulePo,new BigDecimal(1));
//            log.setStatusCode(1);
//            groupOnRuleService.log(log);
//            groupOnRuleService.update(grouponRulePo);
//            //如果是下架操作的话究竟更不更改其他信息这个点不明确
//            return ResponseUtil.ok(grouponRulePo);
//        }else if(inTime==false){
//            //预售未开始或者已经结束可以修改信息
//
//            try {
//                if (groupOnRuleService.update(grouponRulePo) == 0) {
//                    groupOnRuleService.log(log);
//                    return ResponseUtil.fail(721,"团购规则修改失败");
//                }
//            } catch (Exception e) {
//                return ResponseUtil.fail(721,"团购规则修改失败");
//            }
//            log.setStatusCode(1);
//            groupOnRuleService.log(log);
//            return ResponseUtil.ok(grouponRulePo);
//        }else{
//            //在预售开始到结束时间内且未作废的情况，不能改动信息
//            groupOnRuleService.log(log);
//            return ResponseUtil.fail(721,"团购规则修改失败");
//        }
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
    public Object delete(HttpServletRequest request, @PathVariable Integer id){
        String adminid= request.getHeader("id");
        if (adminid==null){
            return ResponseUtil.unlogin();
        }
        Log log = LogUtil.newLog("删除团购", id, Integer.valueOf(adminid), 3, request.getRemoteAddr());

        GrouponRulePo grouponRulePo=new GrouponRulePo();
        grouponRulePo.setId(id);

        GrouponRulePo byId = null;
        try {
            byId = groupOnRuleService.findById(id);
        } catch (Exception e) {
            return ResponseUtil.fail(720,"该团购规则是无效团购规则");
        }
        LocalDateTime now = LocalDateTime.now();
        if (byId==null){
            groupOnRuleService.log(log);
            return ResponseUtil.fail(720,"该团购规则是无效团购规则");
        }
        if (byId.getStartTime().isBefore(now)&&byId.getEndTime().isAfter(now)){
            groupOnRuleService.log(log);
            return ResponseUtil.fail(723,"团购规则删除失败");
        }

        int delete = 0;
        try {
            delete = groupOnRuleService.delete(grouponRulePo);
        } catch (Exception e) {
            return ResponseUtil.fail(723,"团购规则删除失败");
        }
        if (delete==0){
            groupOnRuleService.log(log);
            return ResponseUtil.fail(723,"团购规则删除失败");
        }
        log.setStatusCode(1);
        groupOnRuleService.log(log);
        return ResponseUtil.ok();
    }


    /** 
    * @Description: 用户查看某个团购详情 
    * @Param: [id] 
    * @return: java.lang.Object 
    * @Author: Zhou Linhui
    * @Date: 2019/12/17 
    */ 
    @GetMapping("/grouponRules/{id}")
    public Object userdetail(@PathVariable Integer id){
        GrouponRuleVo grouponRuleVo= new GrouponRuleVo();

        GrouponRulePo grouponRulePo = null;
        try {
            grouponRulePo = groupOnRuleService.findById(id);
        } catch (Exception e) {
            return ResponseUtil.fail(720,"该团购规则是无效团购规则");
        }
        if (grouponRulePo==null){
            return ResponseUtil.fail(720,"该团购规则是无效团购规则");
        }
        GoodsPo grouponGoods = groupOnRuleService.getGrouponGoods(grouponRulePo);
        grouponRuleVo.setGoodsPo(grouponGoods);
        grouponRuleVo.setGrouponRulePo(grouponRulePo);
        return ResponseUtil.ok(grouponRuleVo);
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
        if (rulesList.size() == 0){
            return ResponseUtil.ok();
        }
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
    public Object adminList(HttpServletRequest request,
                            @RequestParam(defaultValue = "1") Integer page,
                            @RequestParam(defaultValue = "10") Integer limit){
        String adminid= request.getHeader("id");
        if (adminid==null){
            return ResponseUtil.unlogin();
        }
        List<GrouponRulePo> rulesList = groupOnRuleService.adminFindGrouponRulePos(page,limit);
        Log log = LogUtil.newLog("查看团购商品", null, Integer.valueOf(adminid), 0, request.getRemoteAddr());
        if (rulesList.size()==0){
            groupOnRuleService.log(log);
            return ResponseUtil.ok();
        }
        List<GrouponRuleVo> grouponRuleVoList=new ArrayList<>();
        for (GrouponRulePo grouponRulePo : rulesList) {
            GoodsPo grouponGoods = groupOnRuleService.getGrouponGoods(grouponRulePo);
            GrouponRuleVo grouponRuleVo=new GrouponRuleVo();
            grouponRuleVo.setGrouponRulePo(grouponRulePo);
            grouponRuleVo.setGoodsPo(grouponGoods);
            grouponRuleVoList.add(grouponRuleVo);
        }
        log.setStatusCode(1);
        groupOnRuleService.log(log);
        return ResponseUtil.ok(grouponRuleVoList);
    }

    @PostMapping("/discount/orders")
    public Object discountOrder(@RequestBody Order order){
        Integer couponId=order.getCouponId();
        if(couponId!=null){

            //使用优惠券的普通订单
            List<OrderItem> oldOrderItems=order.getOrderItemList();
            Integer userId=order.getUserId();
            List<OrderItem> newOrderItems=couponService.calcDiscount(oldOrderItems,couponId);

            //使用的优惠券状态置成已使用
            couponService.updateUserCouponStatus(userId,couponId);

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

                //这里必须在GoodsPo里面找到goodsId
                Integer goodsId=item.getProduct().getGoodsId();
                //使用goodsId去预售规则和团购规则中查找
                List<OrderItem> orderItemList1=new ArrayList<>();

                //是团购订单,itemType设置成2
                if(groupOnRuleService.isGrouponOrder(goodsId)==true){

                    item.setItemType(2);
                    orderItemList1.add(item);
                    order.setOrderItemList(orderItemList1);
                }
                else {
                    //判断是否是预售订单
                    PresaleRuleVo ruleVo = presaleService.isPresaleOrder(item);
                    if ( ruleVo!= null) {
                        PresaleRule rule = ruleVo.getPresaleRule();
                        List<OrderItem> orderItemList2 = new ArrayList<>();
                        item.setItemType(1);
                        List<Payment> payments = presaleService.presalePayment(order, rule);
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
        if (grouponRulePo.getStartTime().isAfter(grouponRulePo.getEndTime())){
            return ResponseUtil.badArgument();
        }
        return null;
    }

}
