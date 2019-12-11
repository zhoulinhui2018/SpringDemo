package xmu.oomall.discount.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import xmu.oomall.discount.domain.GroupOnRule;
import xmu.oomall.discount.mapper.GroupOnRuleMapper;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class GroupOnDao {
    @Autowired
    private GroupOnRuleMapper groupOnRuleMapper;

    public GroupOnRule add(GroupOnRule groupOnRule){
        groupOnRule.setGmtCreate(LocalDateTime.now());
        groupOnRule.setBeDeleted(false);
        groupOnRule.setStatusCode(true);
        groupOnRule.setStartTime(LocalDateTime.now());
        int id= groupOnRuleMapper.add(groupOnRule);
        System.out.println("id "+String.valueOf(id));
        return groupOnRule;
    }

    public GroupOnRule findById(Integer id){
        return groupOnRuleMapper.findGroupOnRuleById(id);
    }

    public int update(GroupOnRule groupOnRule){
        groupOnRule.setGmtModified(LocalDateTime.now());
        return groupOnRuleMapper.updateById(groupOnRule);
    }

    public int delete(GroupOnRule groupOnRule){
        return groupOnRuleMapper.delete(groupOnRule);
    }

    public List<GroupOnRule> searchGrouponGoods(Integer goodsId){
        return groupOnRuleMapper.searchGrouponGoods(goodsId);
    }
}
