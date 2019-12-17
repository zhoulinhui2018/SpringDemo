package xmu.oomall.discount.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import xmu.oomall.discount.controller.vo.PresaleRuleVo;
import xmu.oomall.discount.domain.*;
import xmu.oomall.discount.domain.Promotion.PresaleRule;
import xmu.oomall.discount.mapper.PresaleMapper;

import javax.persistence.criteria.CriteriaBuilder;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PresaleDao {
    @Autowired

    private PresaleMapper presaleMapper;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    /**
     * 管理员添加预售规则
     * @param presaleRule
     */
    public void add(PresaleRule presaleRule){
        presaleRule.setGmtCreate(LocalDateTime.now());
        presaleRule.setGmtModified(LocalDateTime.now());
        presaleRule.setBeDeleted(false);
        presaleMapper.add(presaleRule);
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
        PresaleRuleVo ruleVo=new PresaleRuleVo();
        ruleVo.setPresaleRule(rule);
        Integer goodsId=rule.getGoodsId();
        //根据商品ID调用商品模块的服务获取GoodsPo
        RestTemplate restTemplate = new RestTemplate();
        ServiceInstance instance = loadBalancerClient.choose("Goods");
        String reqURL = String.format("http://%s:%s", instance.getHost(), instance.getPort() + "/goods/{id}");
        Goods goods= restTemplate.getForObject(reqURL, Goods.class,goodsId);
        ruleVo.setGoodsPo(goods);
        return ruleVo;
    }

    /**
     * 根据goodsId找到所有的预售规则
     * @param goodsId
     * @return
     */
    public List<PresaleRuleVo> findByGoodsId(Integer goodsId) {
        List<PresaleRule>  ruleList=presaleMapper.findByGoodsId(goodsId);

        //根据商品ID调用商品模块的服务获取GoodsPo
        RestTemplate restTemplate = new RestTemplate();
        ServiceInstance instance = loadBalancerClient.choose("Goods");
        String reqURL = String.format("http://%s:%s", instance.getHost(), instance.getPort() + "/goods/{id}");
        Goods goods= restTemplate.getForObject(reqURL, Goods.class,goodsId);

        List<PresaleRuleVo> ruleVoList=new ArrayList<>();
        PresaleRuleVo ruleVo=new PresaleRuleVo();

        for(PresaleRule rule:ruleList){
            ruleVo.setPresaleRule(rule);
            ruleVo.setGoodsPo(goods);
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
        List<PresaleRule> presaleRuleList=presaleMapper.findByGoodsId(goodsId);
        List<PresaleRule> selectRuleList=new ArrayList<>();
        LocalDateTime nowTime=LocalDateTime.now();

        for(PresaleRule rule:presaleRuleList){
            //预售规则的付定金的时间段
            LocalDateTime beginTime=rule.getStartTime();
            LocalDateTime endTime=rule.getEndTime();

            if((nowTime.compareTo(beginTime)>=0)&&(nowTime.compareTo(endTime)<=0)){
                return rule;
            }
        }
        return null;
    }



    /**
     * 管理员查看预售规则列表
     * @return
     */
    public List<PresaleRuleVo> findAllPresaleRules(){
        PresaleRuleVo ruleVo=new PresaleRuleVo();
        List<PresaleRuleVo> ruleVoList=new ArrayList<>();
        List<PresaleRule> ruleList= presaleMapper.findAllPresaleRules();
        for(PresaleRule rule:ruleList){
            ruleVo.setPresaleRule(rule);
            Integer goodsId=rule.getGoodsId();
            //根据商品ID调用商品模块的服务获取GoodsPo
            RestTemplate restTemplate = new RestTemplate();
            ServiceInstance instance = loadBalancerClient.choose("Goods");
            String reqURL = String.format("http://%s:%s", instance.getHost(), instance.getPort() + "/goods/{id}");
            Goods goods= restTemplate.getForObject(reqURL, Goods.class,goodsId);
            ruleVo.setGoodsPo(goods);
            ruleVoList.add(ruleVo);
        }
       return ruleVoList;
    }


    /**
     * 用户获取预售商品列表
     * @return
     */
    public List<PresaleRuleVo> findOnPresaleRules() {
        List<PresaleRule> ruleList=presaleMapper.findAllPresaleRules();
        List<PresaleRuleVo> canUsedRuleVoList=new ArrayList<>();
        PresaleRuleVo ruleVo=new PresaleRuleVo();
        for(PresaleRule rule:ruleList){
            LocalDateTime now=LocalDateTime.now();
            if(now.isAfter(rule.getStartTime())&&(now.isBefore(rule.getEndTime()))){
                ruleVo.setPresaleRule(rule);
                Integer goodsId=rule.getGoodsId();
                //根据商品ID调用商品模块的服务获取GoodsPo
                RestTemplate restTemplate = new RestTemplate();
                ServiceInstance instance = loadBalancerClient.choose("Goods");
                String reqURL = String.format("http://%s:%s", instance.getHost(), instance.getPort() + "/goods/{id}");
                Goods goods= restTemplate.getForObject(reqURL, Goods.class,goodsId);
                ruleVo.setGoodsPo(goods);
                canUsedRuleVoList.add(ruleVo);
            }
        }
        return canUsedRuleVoList;
    }
}
