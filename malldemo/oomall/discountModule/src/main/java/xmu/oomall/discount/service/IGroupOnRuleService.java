package xmu.oomall.discount.service;

import org.springframework.stereotype.Service;
import xmu.oomall.discount.domain.GroupOnRule;

import java.util.List;

@Service
public interface IGroupOnRuleService {
    /**
    * @Description: 管理员新增团购规则
    * @Param: [groupOnRule]
    * @return: xmu.oomall.domain.goods.GroupOnRule
    * @Author: Zhou Linhui
    * @Date: 2019/12/6
    */
    public void add(GroupOnRule groupOnRule);

    /**
    * @Description: 查询单张优惠券
    * @Param: [id]
    * @return: xmu.oomall.domain.goods.GroupOnRule
    * @Author: Zhou Linhui
    * @Date: 2019/12/7
    */
    public GroupOnRule findById(Integer id);

    public int update(GroupOnRule groupOnRule);

    public int delete(GroupOnRule groupOnRule);

    public List<GroupOnRule> searchGrouponGoods(Integer goodsId,Integer page,Integer limit);
}
