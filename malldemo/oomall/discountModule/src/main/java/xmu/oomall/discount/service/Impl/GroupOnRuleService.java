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
    public int getGrouponNumber(GrouponRulePo grouponRulePo){
        RestTemplate restTemplate = new RestTemplate();
        ServiceInstance instance = loadBalancerClient.choose("order");
        String reqURL = String.format("http://%s:%s", instance.getHost(), instance.getPort() + "/orders/GrouponOrders");
        return restTemplate.getForObject(reqURL, Integer.class);
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
        int grouponNumber = this.getGrouponNumber(grouponRulePo);
        GrouponRule strategy = groupOnDao.getStrategy(grouponRulePo);
        GrouponRuleStrategy grouponRuleStrategyBest =new GrouponRuleStrategy();
        for (int i = 0; i < strategy.getStrategy().size(); i++) {
            GrouponRuleStrategy grouponRuleStrategy =  strategy.getStrategy().get(i);
            if (grouponRuleStrategy.getLowerbound()<=grouponNumber){
                grouponRuleStrategyBest=grouponRuleStrategy;
            }
        }
        return grouponRuleStrategyBest;
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
