package xmu.oomall.discount.service.Impl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import xmu.oomall.discount.dao.GroupOnDao;
import xmu.oomall.discount.domain.*;
import xmu.oomall.discount.service.IGroupOnRuleService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

@Service
public class GroupOnRuleService implements IGroupOnRuleService {
    @Autowired
    private GroupOnDao groupOnDao;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

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
    public void refund(List<Payment> payments) {
        RestTemplate restTemplate = new RestTemplate();
        ServiceInstance instance = loadBalancerClient.choose("Order");
        String reqURL = String.format("http://%s:%s", instance.getHost(), instance.getPort() + "/order/grouponOrders/refund");
        restTemplate.postForObject(reqURL,payments,Void.class);
    }

//    @Override
//    public void putOrdersBack(List<Order> orders) {
//        RestTemplate restTemplate = new RestTemplate();
//        ServiceInstance instance = loadBalancerClient.choose("Order");
//        String reqURL = String.format("http://%s:%s", instance.getHost(), instance.getPort() + "/orders");
//        restTemplate.getForObject(reqURL,List.class,orders);
//    }

    @Override
    public void add(GrouponRulePo grouponRulePo) {
        groupOnDao.add(grouponRulePo);
    }

    @Override
    public GrouponRulePo findById(Integer id) {
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
    public List<Order> getGrouponOrders(GrouponRulePo grouponRulePo){
        RestTemplate restTemplate = new RestTemplate();
        ServiceInstance instance = loadBalancerClient.choose("Order");
        String reqURL = String.format("http://%s:%s", instance.getHost(), instance.getPort() + "/orders/grouponOrders");
        return restTemplate.getForObject(reqURL, List.class);
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
    public GrouponRuleStrategy getAccessStrategy(GrouponRulePo grouponRulePo) {
        List<Order> orders = this.getGrouponOrders(grouponRulePo);
        int grouponNumber=orders.size();
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
        RestTemplate restTemplate = new RestTemplate();
        ServiceInstance instance = loadBalancerClient.choose("GoodsInfo");
        String reqURL = String.format("http://%s:%s", instance.getHost(), instance.getPort() + "/goods/{id}");
        Goods goods = restTemplate.getForObject(reqURL, Goods.class);
        GoodsPo goodsPo=new GoodsPo();
        goodsPo.setId(goods.getId());
        goodsPo.setGmtCreate(goods.getGmtCreate());
        goodsPo.setGmtModified(goods.getGmtModified());
        goodsPo.setName(goods.getName());
        goodsPo.setGoodsSn(goods.getGoodsSn());
        goodsPo.setShortName(goods.getShortName());
        goodsPo.setDescription(goods.getDescription());
        goodsPo.setBrief(goods.getBrief());
        goodsPo.setPicUrl(goods.getPicUrl());
        goodsPo.setDetail(goods.getDetail());
        goodsPo.setStatusCode(goods.getStatusCode());
        goodsPo.setShareUrl(goods.getShareUrl());
        goodsPo.setGallery(goods.getGallery());
        goodsPo.setGoodsCategoryId(goods.getGoodsCategoryId());
        goodsPo.setBrandId(goods.getBrandId());
        goodsPo.setBeDeleted(goods.getBeDeleted());
        goodsPo.setWeight(goods.getWeight());
        goodsPo.setVolume(goods.getVolume());
        goodsPo.setSpecialFreightId(goods.getSpecialFreightId());
        goodsPo.setBeSpecial(goods.getBeSpecial());
        goodsPo.setPrice(goods.getPrice());
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
