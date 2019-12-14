package xmu.oomall.discount.service;

import org.springframework.stereotype.Service;
import xmu.oomall.discount.domain.GrouponRulePo;
import xmu.oomall.discount.domain.GrouponRuleStrategy;

import java.util.List;

@Service
public interface IGroupOnRuleService {
    /** 
    * @Description: 获得当前团购达成的团购策略 
    * @Param: [grouponRulePo] 
    * @return: xmu.oomall.discount.domain.GrouponRuleStrategy 
    * @Author: Zhou Linhui
    * @Date: 2019/12/13 
    */ 
    public GrouponRuleStrategy getAccessStrategy(GrouponRulePo grouponRulePo);

    /** 
    * @Description: 搜索目前时间点已完成团购 
    * @Param: [] 
    * @return: java.util.List<xmu.oomall.discount.domain.GrouponRule> 
    * @Author: Zhou Linhui
    * @Date: 2019/12/13 
    */ 
    public List<GrouponRulePo> findFinishedGrouponRules();
    
    /**
    * @Description: 获得某个团购成团人数
    * @Param: [grouponRule]
    * @return: int
    * @Author: Zhou Linhui
    * @Date: 2019/12/13
    */
    public int getGrouponNumber(GrouponRulePo grouponRulePo);
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