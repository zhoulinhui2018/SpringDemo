package xmu.oomall.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;
import xmu.oomall.domain.goods.GroupOnRule;

import java.util.List;

@Service
@Mapper
public interface GroupOnRuleMapper {
    public int add(GroupOnRule groupOnRule);

    public GroupOnRule findGroupOnRuleById(Integer id);

    public int updateById(GroupOnRule groupOnRule);

    public int delete(GroupOnRule groupOnRule);

    public List<GroupOnRule> searchGrouponGoods(Integer goodsId);
}