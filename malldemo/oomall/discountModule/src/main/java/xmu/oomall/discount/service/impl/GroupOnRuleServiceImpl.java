package xmu.oomall.discount.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import xmu.oomall.discount.dao.GroupOnDao;
import xmu.oomall.discount.domain.*;
import xmu.oomall.discount.service.IGroupOnRuleServiceImpl;
import xmu.oomall.discount.util.JacksonUtil;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class GroupOnRuleServiceImpl implements IGroupOnRuleServiceImpl {
    @Autowired
    private GroupOnDao groupOnDao;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Override
    public List<GrouponRulePo> findGrouponRuleByGoodsId(Integer goodsId,Integer page,Integer limit) {
        PageHelper.startPage(page,limit);
        return groupOnDao.findGrouponByGoodsId(goodsId);
    }

    @Override
    public Boolean canAdd(GrouponRulePo grouponRulePo) {
        Integer goodsId = grouponRulePo.getGoodsId();
        LocalDateTime now=LocalDateTime.now();
        List<GrouponRulePo> grouponByGoodsId = groupOnDao.findGrouponByGoodsId(goodsId);
        if (grouponByGoodsId==null){
            return true;
        }else {
            for (int i = 0; i < grouponByGoodsId.size(); i++) {
                GrouponRulePo rulePo =  grouponByGoodsId.get(i);
                if (rulePo.getStartTime().isBefore(now)&& rulePo.getEndTime().isAfter(now)){
                    return false;
                }
            }return true;
        }
    }

    @Override
    public void returnBackRate(GrouponRulePo grouponRulePo,BigDecimal rate) {
        RestTemplate restTemplate = new RestTemplate();
        ServiceInstance instance = loadBalancerClient.choose("orderService");
        System.out.println(instance.getHost());
        System.out.println(instance.getPort());
        String reqURL = String.format("http://%s:%s", instance.getHost(), instance.getPort() + "/order/grouponOrders/refund");
        restTemplate.postForObject(reqURL,grouponRulePo,Object.class,rate);
    }

    @Override
    public void log(Log log){
        RestTemplate restTemplate = new RestTemplate();
        ServiceInstance instance = loadBalancerClient.choose("logService");
        String reqURL = String.format("http://%s:%s", instance.getHost(), instance.getPort() + "/logs");
        restTemplate.postForObject(reqURL,log,Log.class);
    }




    @Override
    public boolean isGrouponOrder(Integer goodsId) {
        List<GrouponRulePo> availableGrouponRules = groupOnDao.findAvailableGrouponRules();
        for (GrouponRulePo availableGrouponRule : availableGrouponRules) {
            Integer goodsId1 = availableGrouponRule.getGoodsId();
            if (goodsId.equals(goodsId1)){
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Payment> getGrouponPayment(Order order){
        BigDecimal price=order.getIntegralPrice();
        LocalDateTime now = LocalDateTime.now();
        Payment grouponPayment = new Payment();
        grouponPayment.setActualPrice(price);
        //默认最大付款间隔是30min
        grouponPayment.setEndTime(now.plusMinutes(30));
        List<Payment> list=new ArrayList<>();
        list.add(grouponPayment);
        return list;
    }


    @Override
    public void add(GrouponRulePo grouponRulePo) throws Exception {
        groupOnDao.add(grouponRulePo);
    }

    @Override
    public GrouponRulePo findById(Integer id) throws Exception{
        return groupOnDao.findById(id);
    }

    @Override
    public int delete(GrouponRulePo grouponRulePo) {
        return groupOnDao.delete(grouponRulePo);
    }

    @Override
    public int update(GrouponRulePo grouponRulePo) {
        return groupOnDao.update(grouponRulePo);
    }


    @Override
    public Integer getGrouponNum(GrouponRulePo grouponRulePo){
        RestTemplate restTemplate = new RestTemplate();
        ServiceInstance instance = loadBalancerClient.choose("orderService");
        String reqURL = String.format("http://%s:%s", instance.getHost(), instance.getPort() + "/orders/grouponOrders");
        String object = restTemplate.getForObject(reqURL, String.class, grouponRulePo);
        Integer num = JacksonUtil.parseObject(object, "data", Integer.class);
        return num;
    }

    @Override
    public List<GrouponRulePo> findFinishedGrouponRules() {
        List<GrouponRulePo> availableGrouponRules = groupOnDao.findAvailableGrouponRules();
        Iterator<GrouponRulePo> iterator = availableGrouponRules.iterator();
        while (iterator.hasNext()){
            GrouponRulePo grouponRulePo = iterator.next();
            LocalDateTime endTime = grouponRulePo.getEndTime();
            LocalDateTime now = LocalDateTime.now();
            if (now.minusDays(1).isBefore(endTime)){
                iterator.remove();
            }
        }
        return availableGrouponRules;
    }

    @Override
    public GrouponRuleStrategy getAccessStrategy(GrouponRulePo grouponRulePo,Integer grouponNumber) {
        GrouponRule strategy = groupOnDao.getStrategy(grouponRulePo);
        boolean isEnough= false;
        GrouponRuleStrategy grouponRuleStrategyBest =new GrouponRuleStrategy();
        for (int i = 0; i < strategy.getStrategy().size(); i++) {
            GrouponRuleStrategy grouponRuleStrategy =  strategy.getStrategy().get(i);
            if (grouponRuleStrategy.getLowerbound()<=grouponNumber){
                grouponRuleStrategyBest=grouponRuleStrategy;
                isEnough=true;
            }
        }
        if (isEnough==true){
            return grouponRuleStrategyBest;
        }
        else {
            GrouponRuleStrategy grouponRuleStrategyBest2 =new GrouponRuleStrategy();
            grouponRuleStrategyBest2.setRate(new BigDecimal(1));
            return grouponRuleStrategyBest2;
        }
    }

    @Override
    public GoodsPo getGrouponGoods(GrouponRulePo grouponRulePo) {
        Integer goodsId=grouponRulePo.getGoodsId();
        RestTemplate restTemplate = new RestTemplate();
        ServiceInstance instance = loadBalancerClient.choose("goodsService");
        String reqURL = String.format("http://%s:%s", instance.getHost(), instance.getPort() + "/inner/goods/"+goodsId);
        Object result= restTemplate.getForObject(reqURL,Object.class);
        Map<String,Object> haspMap=(Map<String,Object>)result;
        ObjectMapper mapper = new ObjectMapper();
        GoodsPo goodsPo = mapper.convertValue(haspMap.get("data"),GoodsPo.class);
        return goodsPo;
    }

    @Override
    public List<GrouponRulePo> findGrouponRulePos(Integer page, Integer limit) {
        PageHelper.startPage(page,limit);
        List<GrouponRulePo> availableGrouponRules = groupOnDao.findAvailableGrouponRules();
        return availableGrouponRules;
    }

    @Override
    public List<GrouponRulePo> adminFindGrouponRulePos(Integer page, Integer limit) {
        PageHelper.startPage(page,limit);
        List<GrouponRulePo> grouponRulePos=groupOnDao.adminFindGrouponRules();
        return grouponRulePos;
    }
}
