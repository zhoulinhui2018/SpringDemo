package xmu.oomall.discount.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import xmu.oomall.discount.controller.vo.GrouponRuleVo;
import xmu.oomall.discount.dao.GroupOnDao;
import xmu.oomall.discount.domain.*;
import xmu.oomall.discount.service.Impl.GroupOnRuleService;
import xmu.oomall.discount.util.ResponseUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("")
public class DiscountController {
    @Autowired
    private GroupOnRuleService groupOnRuleService;

    @Autowired
    private GroupOnDao groupOnDao;

    @Scheduled(cron = "0/3 * * * * ?")
    public Object executeAllGroupon(){

        List<GrouponRulePo> finishedGrouponRules = groupOnRuleService.findFinishedGrouponRules();
        for (GrouponRulePo finishedGrouponRule : finishedGrouponRules) {
            List<Order> grouponOrders = groupOnRuleService.getGrouponOrders(finishedGrouponRule);
            GrouponRuleStrategy accessStrategy = groupOnRuleService.getAccessStrategy(finishedGrouponRule);
            BigDecimal rate = accessStrategy.getRate();
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
                groupOnRuleService.refund(payment);
            }
            groupOnRuleService.putOrdersBack(grouponOrders);

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
    public Object update(@PathVariable Integer id,@RequestBody GrouponRulePo grouponRulePo){
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
}
