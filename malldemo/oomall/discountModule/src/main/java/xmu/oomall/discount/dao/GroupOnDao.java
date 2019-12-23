package xmu.oomall.discount.dao;

import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import xmu.oomall.discount.domain.GrouponRule;
import xmu.oomall.discount.domain.GrouponRulePo;
import xmu.oomall.discount.domain.GrouponRuleStrategy;
import xmu.oomall.discount.mapper.GroupOnRuleMapper;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/** 
* @Description:  
* @Param:  
* @return:  
* @Author: Zhou Linhui
* @Date: 2019/12/23 
*/ 
@Repository
public class GroupOnDao {
    @Autowired
    private GroupOnRuleMapper groupOnRuleMapper;

    public List<GrouponRulePo> findGrouponByGoodsId(Integer goodsId){
        return groupOnRuleMapper.findGrouponByGoodsId(goodsId);
    }


    public GrouponRule getStrategy(GrouponRulePo grouponRulePo){
        System.out.println("getStrategy参数：");
        String jsonString = grouponRulePo.getGrouponLevelStrategy();
        System.out.println("jsonString = "+ jsonString);
        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        System.out.println("test");
        Map classmap = new HashMap<>(16);
        classmap.put("strategy", GrouponRuleStrategy.class);
        System.out.println("test2");

        GrouponRule grouponRule= (GrouponRule) JSONObject.toBean(jsonObject, GrouponRule.class, classmap);
        System.out.println("test3");

        for (int i = 0; i < grouponRule.getStrategy().size(); i++) {
            GrouponRuleStrategy strategy =  grouponRule.getStrategy().get(i);
            System.out.println("lower bound"+strategy.getLowerbound());
            System.out.println("upper bound"+strategy.getUpperbound());
            System.out.println("rate"+strategy.getRate());
        }
        return grouponRule;
    }

    public GrouponRulePo add(GrouponRulePo grouponRulePo) throws Exception{
        grouponRulePo.setGmtCreate(LocalDateTime.now());
        grouponRulePo.setBeDeleted(false);
        grouponRulePo.setStatusCode(true);
        grouponRulePo.setGmtModified(LocalDateTime.now());
        int id= groupOnRuleMapper.add(grouponRulePo);
        System.out.println("id "+String.valueOf(id));
        return grouponRulePo;
    }

    public GrouponRulePo findById(Integer id) throws Exception{
        return groupOnRuleMapper.findGroupOnRuleById(id);
    }

    public int update(GrouponRulePo grouponRulePo){
        grouponRulePo.setGmtModified(LocalDateTime.now());
        return groupOnRuleMapper.updateById(grouponRulePo);
    }

    public int delete(GrouponRulePo grouponRulePo){
        GrouponRulePo grouponRulePoNew=new GrouponRulePo();
        grouponRulePoNew.setGmtModified(LocalDateTime.now());
        grouponRulePoNew.setId(grouponRulePo.getId());
        groupOnRuleMapper.updateById(grouponRulePoNew);
        return groupOnRuleMapper.delete(grouponRulePo);
    }

    public List<GrouponRulePo> searchGrouponGoods(Integer goodsId){
        return groupOnRuleMapper.searchGrouponGoods(goodsId);
    }

    public List<GrouponRulePo> findAvailableGrouponRules(){
        List<GrouponRulePo> availableGrouponRules = groupOnRuleMapper.findAvailableGrouponRules();
        LocalDateTime  now = LocalDateTime.now();
        Iterator<GrouponRulePo> iterator = availableGrouponRules.iterator();
        while (iterator.hasNext()){
            GrouponRulePo next = iterator.next();
            if (now.isBefore(next.getStartTime()) || now.isAfter(next.getEndTime())){
                iterator.remove();
            }
        }
        return availableGrouponRules;
    }


    public List<GrouponRulePo> adminFindGrouponRules(){
        return groupOnRuleMapper.adminFindGrouponRules();
    }
}
