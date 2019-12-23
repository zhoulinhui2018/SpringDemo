package xmu.oomall.discount.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;
import xmu.oomall.discount.domain.GrouponRulePo;

import java.util.List;

/**
 * Demo class GroupOnRuleMapper
 *
 * @author Zhang Yaqing
 * @date 2019/12/20
 */
@Service
@Mapper
public interface GroupOnRuleMapper {

    /**
     * 用户使用优惠券后更改状态
     * @param goodsId 1
     * @return GrouponRulePo
     */
    public List<GrouponRulePo> findGrouponByGoodsId(Integer goodsId);
    /**
     * 用户使用优惠券后更改状态
     * @param grouponRulePo 1
     * @return 1
     */
    public int add(GrouponRulePo grouponRulePo);
    /**
     * 用户使用优惠券后更改状态
     * @param id 1
     * @return GrouponRulePo
     */
    public GrouponRulePo findGroupOnRuleById(Integer id);
    /**
     * 用户使用优惠券后更改状态
     * @param grouponRulePo 1
     * @return 1
     */
    public int updateById(GrouponRulePo grouponRulePo);
    /**
     * 用户使用优惠券后更改状态
     * @param grouponRulePo 1
     * @return 1
     */
    public int delete(GrouponRulePo grouponRulePo);
    /**
     * 用户使用优惠券后更改状态
     * @param goodsId 1
     * @return 1
     */
    public List<GrouponRulePo> searchGrouponGoods(Integer goodsId);
    /**
     * 用户使用优惠券后更改状态
     * @return 1
     */
    public List<GrouponRulePo> findAvailableGrouponRules();
    /**
     * 用户使用优惠券后更改状态
     * @return GrouponRulePo
     */
    public List<GrouponRulePo> adminFindGrouponRules();
}
