package xmu.oomall.discount.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;
import xmu.oomall.discount.domain.GrouponRulePo;

import java.util.List;

@Service
@Mapper
public interface GroupOnRuleMapper {
    public int add(GrouponRulePo grouponRulePo);

    public GrouponRulePo findGroupOnRuleById(Integer id);

    public int updateById(GrouponRulePo grouponRulePo);

    public int delete(GrouponRulePo grouponRulePo);

    public List<GrouponRulePo> searchGrouponGoods(Integer goodsId);
}
