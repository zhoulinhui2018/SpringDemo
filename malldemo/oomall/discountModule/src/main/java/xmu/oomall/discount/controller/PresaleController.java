package xmu.oomall.discount.controller;

import com.alibaba.druid.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import xmu.oomall.discount.controller.vo.PresaleRuleVo;
import xmu.oomall.discount.domain.Order;
import xmu.oomall.discount.domain.Payment;
import xmu.oomall.discount.domain.Promotion.PresaleRule;
import xmu.oomall.discount.service.Impl.PresaleServiceImpl;
import xmu.oomall.util.ResponseUtil;

import javax.persistence.criteria.CriteriaBuilder;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("")
public class PresaleController {
    @Autowired
    private PresaleServiceImpl presaleService;


    /**
     * 判断presaleRule是否符合规范
     * @param presaleRule
     * @return
     */
    private Object validate(PresaleRule presaleRule) {
       LocalDateTime startTime=presaleRule.getStartTime();
       LocalDateTime adEndTime=presaleRule.getAdEndTime();
       LocalDateTime finalStartTime=presaleRule.getFinalStartTime();
       LocalDateTime endTime=presaleRule.getEndTime();
       Boolean statusCode=presaleRule.getStatusCode();
       Integer goodsId=presaleRule.getGoodsId();
       BigDecimal deposit=presaleRule.getDeposit();
       BigDecimal finalPayment=presaleRule.getFinalPayment();
       if (StringUtils.isEmpty(String.valueOf(startTime))) {
            return ResponseUtil.badArgument();
        }
        else if(StringUtils.isEmpty(String.valueOf(adEndTime))){
            return ResponseUtil.badArgument();
        }
        else if(StringUtils.isEmpty(String.valueOf(finalStartTime))){
            return ResponseUtil.badArgument();
        }
        else if(StringUtils.isEmpty(String.valueOf(endTime))){
            return ResponseUtil.badArgument();
        }
        else if(StringUtils.isEmpty(String.valueOf(statusCode))){
            return  ResponseUtil.badArgument();
        }
        else if(StringUtils.isEmpty(String.valueOf(goodsId))){
            return ResponseUtil.badArgument();
        }
        else if(StringUtils.isEmpty(String.valueOf(deposit))){
            return ResponseUtil.badArgument();
        }
        else if(StringUtils.isEmpty(String.valueOf(finalPayment))){
            return ResponseUtil.badArgument();
        }

        return null;
    }
    /**
     * @Description: 管理员新增预售规则
     * @Param: [presaleRule]
     * @return: java.lang.Object
     * @Author: Zhang Yaqing
     * @Date: 2019/12/10
     */
    @PostMapping("/presaleRule")
    public Object create(@RequestBody PresaleRule presaleRule){
        Object error = validate(presaleRule);
        if (error != null) {
            return error;
        }
        presaleService.add(presaleRule);
        return ResponseUtil.ok(presaleRule);
    }

    /**
     * @Description: 管理员查看某个预售规则详情
     * @Param: [id]
     * @return: java.lang.Object
     * @Author: Zhang Yaqing
     * @Date: 2019/12/10
     */
    @GetMapping("/presaleRule/{id}")
    public Object detail(@PathVariable Integer id){
        PresaleRuleVo presaleRuleVo = presaleService.findById(id);
        return ResponseUtil.ok(presaleRuleVo);
    }

    /**
     * @Description: 管理员修改预售规则信息
     * 附加：管理员可以作废预售活动，预售活动作废后，所有未付尾款的预售订单也一并作废，需要退定金
     * @Param: [id, presaleRule]
     * @return: java.lang.Object
     * @Author: Zhang Yaqing
     * @Date: 2019/12/16
     */
    @PutMapping("/presaleRule/{id}")
    public Object update(@PathVariable Integer id,@RequestBody PresaleRule presaleRule){
        Object error = validate(presaleRule);
        if (error != null) {
            return error;
        }
        presaleRule.setId(id);

        PresaleRuleVo ruleVoInDB=presaleService.findById(id);
        PresaleRule ruleInDB=ruleVoInDB.getPresaleRule();


        Boolean oldStatusCode=ruleInDB.getStatusCode();
        Boolean newStatusCode=presaleRule.getStatusCode();
        LocalDateTime beginTime=ruleInDB.getStartTime();
        LocalDateTime endTime=ruleInDB.getEndTime();
        LocalDateTime nowTime=LocalDateTime.now();

        Boolean inTime=(nowTime.compareTo(beginTime) >= 0) && (nowTime.compareTo(endTime) <= 0);
        Boolean changeStatus= (newStatusCode==false) && (oldStatusCode==true);


        //作废
        if(inTime==true&&changeStatus==true) {
            //先修改预售状态
            if (presaleService.update(presaleRule) == 0) {
                return ResponseUtil.updatedDataFailed();
            }
            //再进行退款操作
            //新建orderList，获取所有涉及此presaleRule的订单
            List<Order> orderList=presaleService.getPresaleRuleOrders(presaleRule);
            //新建paymentList，获取所有订单需要退款的金额
            List<Payment> paymentList=presaleService.getPaymentList(orderList);
            //调用payment模块的接口，处理退款
            presaleService.presaleRefund(paymentList);
            return ResponseUtil.ok(presaleRule);

        }else if(inTime==false){
            //预售未开始或者已经结束可以修改信息
            if (presaleService.update(presaleRule) == 0) {
                return ResponseUtil.updatedDataFailed();
            }
            return ResponseUtil.ok(presaleRule);

        }else{
            //在预售开始到结束时间内且未作废的情况，不能改动信息
            return ResponseUtil.updatedDataFailed();
        }
    }

    /**
     * @Description: 管理员删除预售规则
     * @Param: [id, presaleRule]
     * @return: java.lang.Object
     * @Author: Zhang Yaqing
     * @Date: 2019/12/10
     */
    @DeleteMapping("/presaleRule/{id}")
    public Object delete(@PathVariable Integer id){
        PresaleRuleVo ruleInDBVo=presaleService.findById(id);
        PresaleRule ruleInDB=ruleInDBVo.getPresaleRule();
        Boolean statusCode=ruleInDB.getStatusCode();
        LocalDateTime beginTime=ruleInDB.getStartTime();
        LocalDateTime endTime=ruleInDB.getEndTime();
        LocalDateTime nowTime=LocalDateTime.now();
        //预售规则未失效
        if(statusCode==true) {
            //在预售开始到结束时间内不能删除优惠券
            if ((nowTime.compareTo(beginTime) >= 0) && (nowTime.compareTo(endTime) <= 0)) {
                return ResponseUtil.fail();
            }
            presaleService.delete(id);
            return ResponseUtil.ok();
        }else {//预售规则已失效
            presaleService.delete(id);
            return ResponseUtil.ok();
        }
    }

    /**
     * 用户根据商品ID搜索预售规则
     * @param goodsId
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("/presaleRules")
    public Object selectPresaleRule(@RequestParam Integer goodsId,@RequestParam Integer page,@RequestParam Integer limit){
        List<PresaleRuleVo> list=presaleService.findPresaleRule(goodsId,page,limit);
        return ResponseUtil.ok(list);
    }

    /**
     * 管理员查看预售规则列表
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("/admins/presaleGoods")
    public Object findAllPresaleRules(@RequestParam Integer page,@RequestParam Integer limit){
        List<PresaleRuleVo> list=presaleService.findAllPresaleRules(page, limit);
        return ResponseUtil.ok(list);
    }

    /**
     * 用户根据ID查看预售规则详情
     * @param id
     * @return
     */
    @GetMapping("/presaleRules/{id}")
    public Object getPresaleRuleById(@PathVariable Integer id){
        PresaleRuleVo presaleRuleVo=presaleService.findById(id);
        return ResponseUtil.ok(presaleRuleVo);
    }


    /**
     * 用户查看预售商品列表
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("/presaleGoods")
    public Object getPresaleGoods(@RequestParam Integer page,@RequestParam Integer limit){
        List<PresaleRuleVo> ruleVos=presaleService.findOnPresaleRules(page, limit);
        return ResponseUtil.ok(ruleVos);
    }
}
