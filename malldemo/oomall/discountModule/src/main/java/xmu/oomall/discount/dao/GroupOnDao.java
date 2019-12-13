package xmu.oomall.discount.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import xmu.oomall.discount.domain.GrouponRulePo;
import xmu.oomall.discount.mapper.GroupOnRuleMapper;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class GroupOnDao {
    @Autowired
    private GroupOnRuleMapper groupOnRuleMapper;

    public GrouponRulePo add(GrouponRulePo grouponRulePo){
        grouponRulePo.setGmtCreate(LocalDateTime.now());
        grouponRulePo.setIsDeleted(false);
        grouponRulePo.setStatusCode(true);
        grouponRulePo.setStartTime(LocalDateTime.now());
        grouponRulePo.setGmtModified(LocalDateTime.now());
        int id= groupOnRuleMapper.add(grouponRulePo);
        System.out.println("id "+String.valueOf(id));
        return grouponRulePo;
    }

    public GrouponRulePo findById(Integer id){
        return groupOnRuleMapper.findGroupOnRuleById(id);
    }

    public int update(GrouponRulePo grouponRulePo){
        grouponRulePo.setGmtModified(LocalDateTime.now());
        return groupOnRuleMapper.updateById(grouponRulePo);
    }

    public int delete(GrouponRulePo grouponRulePo){
        return groupOnRuleMapper.delete(grouponRulePo);
    }

    public List<GrouponRulePo> searchGrouponGoods(Integer goodsId){
        return groupOnRuleMapper.searchGrouponGoods(goodsId);
    }
}
