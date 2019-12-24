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
 * Demo class GroupOnDao
 *
 * @author Zhou Linhui
 * @date 2019/12/15
 */
@Repository
public class GroupOnDao {
    @Autowired
    private GroupOnRuleMapper groupOnRuleMapper;

    public List<GrouponRulePo> findGrouponByGoodsId(Integer goodsId){
        return groupOnRuleMapper.findGrouponByGoodsId(goodsId);
    }


    public GrouponRule getStrategy(GrouponRulePo grouponRulePo){
        String jsonString = grouponRulePo.getGrouponLevelStrategy();
        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        Map classmap = new HashMap<>(10);
        classmap.put("strategy", GrouponRuleStrategy.class);

        GrouponRule grouponRule= (GrouponRule) JSONObject.toBean(jsonObject, GrouponRule.class, classmap);

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
