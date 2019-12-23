package xmu.oomall.discount.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;
import xmu.oomall.discount.domain.GrouponRulePo;

import java.util.List;

/**
* @Description:
* @Param:
* @return:
* @Author: Zhou Linhui
* @Date: 2019/12/23
*/
@Service
@Mapper
public interface GroupOnRuleMapper {

    /**
    * @Description: sfd
    * @param:  goodsId
    * @return: java.util.List<xmu.oomall.discount.domain.GrouponRulePo>
    * @Author: Zhou Linhui
    * @Date: 2019/12/23
    */
    public List<GrouponRulePo> findGrouponByGoodsId(Integer goodsId);

    public int add(GrouponRulePo grouponRulePo);

    public GrouponRulePo findGroupOnRuleById(Integer id);

    public int updateById(GrouponRulePo grouponRulePo);

    public int delete(GrouponRulePo grouponRulePo);

    public List<GrouponRulePo> searchGrouponGoods(Integer goodsId);

    public List<GrouponRulePo> findAvailableGrouponRules();

    public List<GrouponRulePo> adminFindGrouponRules();
}
