package xmu.oomall.discount.service.Impl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import xmu.oomall.discount.dao.GroupOnDao;
import xmu.oomall.discount.domain.GrouponRule;
import xmu.oomall.discount.domain.GrouponRulePo;
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
    public List<GrouponRulePo> searchGrouponGoods(Integer goodsId, Integer page, Integer limit) {
        PageHelper.startPage(page,limit);
        return groupOnDao.searchGrouponGoods(goodsId);
    }

    public int getGrouponNumber(GrouponRule grouponRule){
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
}
