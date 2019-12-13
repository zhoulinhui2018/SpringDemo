package xmu.oomall.discount.service;

import org.springframework.stereotype.Service;
import xmu.oomall.discount.domain.GrouponRulePo;

import java.util.List;

@Service
public interface IGroupOnRuleService {
    /**
    * @Description: 管理员新增团购规则
    * @Param: [grouponRulePo]
    * @return: xmu.oomall.domain.goods.GrouponRulePo
    * @Author: Zhou Linhui
    * @Date: 2019/12/6
    */
    public void add(GrouponRulePo grouponRulePo);

    /**
    * @Description: 查询单张优惠券
    * @Param: [id]
    * @return: xmu.oomall.domain.goods.GrouponRulePo
    * @Author: Zhou Linhui
    * @Date: 2019/12/7
    */
    public GrouponRulePo findById(Integer id);

    public int update(GrouponRulePo grouponRulePo);

    public int delete(GrouponRulePo grouponRulePo);

    public List<GrouponRulePo> searchGrouponGoods(Integer goodsId, Integer page, Integer limit);
}
