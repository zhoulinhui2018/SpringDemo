package xmu.oomall.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import xmu.oomall.domain.goods.GroupOnRule;
import xmu.oomall.mapper.GroupOnRuleMapper;

@Repository
public class GroupOnDao {
    @Autowired
    private GroupOnRuleMapper groupOnRuleMapper;

    public GroupOnRule add(GroupOnRule groupOnRule){
        int id= groupOnRuleMapper.add(groupOnRule);
        System.out.println("id "+String.valueOf(id));
        return groupOnRule;
    }

    public GroupOnRule findById(Integer id){
        return groupOnRuleMapper.findGroupOnRuleById(id);
    }

    public int update(GroupOnRule groupOnRule){
        return groupOnRuleMapper.updateById(groupOnRule);
    }

    public int delete(GroupOnRule groupOnRule){
        return groupOnRuleMapper.delete(groupOnRule);
    }
}
