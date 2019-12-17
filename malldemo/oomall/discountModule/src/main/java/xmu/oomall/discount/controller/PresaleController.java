package xmu.oomall.discount.controller;

import com.alibaba.druid.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import xmu.oomall.discount.domain.Order;
import xmu.oomall.discount.domain.Payment;
import xmu.oomall.discount.domain.Promotion.PresaleRule;
import xmu.oomall.discount.service.Impl.PresaleServiceImpl;
import xmu.oomall.util.ResponseUtil;

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
        Integer id = presaleRule.getGoodsId();
        if (StringUtils.isEmpty(String.valueOf(id))) {
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
        PresaleRule presaleRule = presaleService.findById(id);
        return ResponseUtil.ok(presaleRule);
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
    public Object update(@PathVariable Integer id,PresaleRule presaleRule){
        Object error = validate(presaleRule);
        if (error != null) {
            return error;
        }
        presaleRule.setId(id);

        PresaleRule ruleInDB=presaleService.findById(id);
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
        PresaleRule ruleInDB=presaleService.findById(id);
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



}
