package xmu.oomall.discount.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import xmu.oomall.discount.controller.vo.PresaleRuleVo;
import xmu.oomall.discount.domain.*;
import xmu.oomall.discount.domain.Promotion.PresaleRule;
import xmu.oomall.discount.mapper.PresaleMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class PresaleDao {
    @Autowired

    private PresaleMapper presaleMapper;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    public GoodsPo getGoodsPoById(Integer goodsId) {
        RestTemplate restTemplate = new RestTemplate();
        ServiceInstance instance = loadBalancerClient.choose("goodsService");
        String reqURL = String.format("http://%s:%s", instance.getHost(), instance.getPort() + "/inner/goods/"+goodsId);
        Object result= restTemplate.getForObject(reqURL,Object.class);
        Map<String,Object> haspMap=(Map<String,Object>)result;
        ObjectMapper mapper = new ObjectMapper();
        GoodsPo goodsPo = mapper.convertValue(haspMap.get("data"),GoodsPo.class);
        return goodsPo;
    }

    /**
     * 管理员添加预售规则
     * @param presaleRule
     */
    public Integer add(PresaleRule presaleRule){
        presaleRule.setGmtCreate(LocalDateTime.now());
        presaleRule.setGmtModified(LocalDateTime.now());
        presaleRule.setBeDeleted(false);
        presaleRule.setStatusCode(true);
        return presaleMapper.add(presaleRule);
    }

    /**
     * 管理员更新预售规则
     * @param presaleRule
     * @return
     */
    public int update(PresaleRule presaleRule){
        presaleRule.setGmtModified(LocalDateTime.now());
        return presaleMapper.updateById(presaleRule);
    }

    /**
     * 管理员根据id删除预售规则
     * @param id
     * @return
     */
    public int delete(Integer id){
        return presaleMapper.deleteById(id);
    }

    /**
     * 根据id找到预售规则
     * @param id
     * @return
     */
    public PresaleRuleVo findById(Integer id){
        PresaleRule rule=presaleMapper.findPresaleRuleById(id);
        if(rule==null){
            return null;
        }
        PresaleRuleVo ruleVo=new PresaleRuleVo();
        ruleVo.setPresaleRule(rule);
        Integer goodsId=rule.getGoodsId();
        ruleVo.setGoodsPo(getGoodsPoById(goodsId));
        return ruleVo;
    }

    /**
     * 根据goodsId找到预售规则
     * @param goodsId
     * @return
     */
    public List<PresaleRuleVo> findByGoodsId(Integer goodsId) {
        List<PresaleRule>  ruleList=presaleMapper.findByGoodsId(goodsId);
        GoodsPo goodsPo= getGoodsPoById(goodsId);
        List<PresaleRuleVo> ruleVoList=new ArrayList<>();
        PresaleRuleVo ruleVo=new PresaleRuleVo();
        for(PresaleRule rule:ruleList){
            ruleVo.setPresaleRule(rule);
            ruleVo.setGoodsPo(goodsPo);
            ruleVoList.add(ruleVo);
        }
        return ruleVoList;
    }


    /**
     * 根据order和presaleRule得出定金和尾款两个payment
     * @param order
     * @return
     */
    public List<Payment> presalePayment(Order order,PresaleRule rule) {
        BigDecimal prePay = BigDecimal.ZERO;
        BigDecimal finalPay = BigDecimal.ZERO;
        LocalDateTime now = LocalDateTime.now();

        for (OrderItem item: order.getOrderItemList()){
            prePay = prePay.add(rule.getDeposit());
            finalPay = finalPay.add(rule.getFinalPayment());
        }

        Payment prePayment = new Payment();
        prePayment.setActualPrice(prePay);
        //默认最大付款间隔是30min
        prePayment.setEndTime(now.plusMinutes(30));
        prePayment.setOrderId(order.getId());

        Payment finalPayment = new Payment();
        finalPayment.setActualPrice(finalPay);
        finalPayment.setBeginTime(rule.getFinalStartTime());
        finalPayment.setEndTime(rule.getEndTime());
        finalPayment.setOrderId(order.getId());

        List<Payment> ret = new ArrayList<>(2);
        ret.add(prePayment);
        ret.add(finalPayment);
        return ret;

    }


    /**
     * 根据商品ID判断是否是预售订单
     * @param goodsId
     * @return
     */
    public PresaleRule isPresaleOrder(Integer goodsId) {
        System.out.println("aaaa");
        List<PresaleRule> presaleRuleList=presaleMapper.findByGoodsId(goodsId);
        System.out.println("a");
        if(presaleRuleList==null){
            System.out.println();
            return null;
        }
        System.out.println("b");
        LocalDateTime nowTime=LocalDateTime.now();

        for(PresaleRule rule:presaleRuleList){
            //预售规则进行的时间段
            LocalDateTime beginTime=rule.getStartTime();
            LocalDateTime endTime=rule.getEndTime();

            if((nowTime.isAfter(beginTime))&&(nowTime.isBefore(endTime))){
                return rule;
            }
        }
        return null;
    }



    /**
     * 管理员查看预售商品列表
     * @return
     */
    public List<PresaleRuleVo> findAllPresaleGoods(){
        List<PresaleRuleVo> ruleVoList=new ArrayList<>();
        PresaleRuleVo ruleVo=new PresaleRuleVo();
        List<PresaleRule> ruleList= presaleMapper.findAllPresaleGoods();
        for(PresaleRule rule:ruleList){
            ruleVo.setPresaleRule(rule);
            Integer goodsId=rule.getGoodsId();
            GoodsPo goodsPo= getGoodsPoById(goodsId);
            ruleVo.setGoodsPo(goodsPo);
            ruleVoList.add(ruleVo);
        }
       return ruleVoList;
    }


    /**
     * 用户获取预售商品列表
     * @return
     */
    public List<PresaleRuleVo> findOnPresaleRules() {
        List<PresaleRule> ruleList=presaleMapper.findAllPresaleGoods();
        List<PresaleRuleVo> canUsedRuleVoList=new ArrayList<>();
        PresaleRuleVo ruleVo=new PresaleRuleVo();
        for(PresaleRule rule:ruleList){
            //上架且没被删除
            if(rule.getStatusCode() && !rule.getBeDeleted()){
                ruleVo.setPresaleRule(rule);
                Integer goodsId=rule.getGoodsId();
                GoodsPo goodsPo= getGoodsPoById(goodsId);
                ruleVo.setGoodsPo(goodsPo);
                canUsedRuleVoList.add(ruleVo);
            }
        }
        return canUsedRuleVoList;
    }

    public int invalidate(Integer id) {
        return presaleMapper.invalidate(id);
    }
}
