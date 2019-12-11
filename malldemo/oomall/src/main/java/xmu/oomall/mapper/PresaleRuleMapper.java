package xmu.oomall.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;
import xmu.oomall.domain.goods.PresaleRule;

import java.util.List;

@Service
@Mapper
public interface PresaleRuleMapper {
    public int add(PresaleRule presaleRule);

    public PresaleRule findPresaleRuleById(Integer id);

    public void updateById(PresaleRule presaleRule);

    public void delete(PresaleRule presaleRule);

}
