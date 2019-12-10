package xmu.oomall.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import xmu.oomall.domain.goods.PresaleRule;
import xmu.oomall.mapper.PresaleRuleMapper;

import java.util.List;

@Repository
public class PresaleRuleDao {
    @Autowired
    private PresaleRuleMapper presaleRuleMapper;

    public void add(PresaleRule presaleRule){
        int id= presaleRuleMapper.add(presaleRule);
    }

    public PresaleRule findById(Integer id){
        return presaleRuleMapper.findPresaleRuleById(id);
    }

    public void update(PresaleRule groupOnRule){
        presaleRuleMapper.updateById(groupOnRule);
    }

    public void delete(PresaleRule groupOnRule){
        presaleRuleMapper.delete(groupOnRule);
    }


}
