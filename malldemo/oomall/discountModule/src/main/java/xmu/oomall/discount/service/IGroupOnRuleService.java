package xmu.oomall.discount.service;

import org.springframework.stereotype.Service;
import xmu.oomall.discount.domain.*;

import java.util.List;

@Service
public interface IGroupOnRuleService {


    /**
     * 管理员操作添加Log
     * @param log
     */
    void log(Log log);


    /**
     * 根据GoodsId判断是否是团购订单
     * @param goodsId
     * @return
     */
    boolean isGrouponOrder(Integer goodsId);

    /**
     * 根据团购订单创建一个payment
     * @param order
     * @return
     */
    List<Payment> getGrouponPayment(Order order);


    void refund(List<Payment> payments);

    /**
    * @Description: 将新订单传回去给order模块
    * @Param:
    * @return:
    * @Author: Zhou Linhui
    * @Date: 2019/12/16
    */
    //void putOrdersBack(List<Order> orders);

    /** 
    * @Description: 获得团购商品 
    * @Param: [grouponRulePo] 
    * @return: xmu.oomall.discount.domain.GoodsPo 
    * @Author: Zhou Linhui
    * @Date: 2019/12/14 
    */ 
    GoodsPo getGrouponGoods(GrouponRulePo grouponRulePo);

    /** 
    * @Description: 获得当前团购达成的团购策略 
    * @Param: [grouponRulePo] 
    * @return: xmu.oomall.discount.domain.GrouponRuleStrategy 
    * @Author: Zhou Linhui
    * @Date: 2019/12/13 
    */ 
    GrouponRuleStrategy getAccessStrategy(GrouponRulePo grouponRulePo);

    /** 
    * @Description: 搜索目前时间点已完成团购 
    * @Param: [] 
    * @return: java.util.List<xmu.oomall.discount.domain.GrouponRule> 
    * @Author: Zhou Linhui
    * @Date: 2019/12/13 
    */ 
    List<GrouponRulePo> findFinishedGrouponRules();
    
    /**
    * @Description: 获得某个团购成团订单
    * @Param: [grouponRule]
    * @return: int
    * @Author: Zhou Linhui
    * @Date: 2019/12/13
    */
    List<Order> getGrouponOrders(GrouponRulePo grouponRulePo);

    /**
    * @Description: 管理员新增团购规则
    * @Param: [grouponRulePo]
    * @return: xmu.oomall.domain.goods.GrouponRulePo
    * @Author: Zhou Linhui
    * @Date: 2019/12/6
    */
    void add(GrouponRulePo grouponRulePo) throws Exception;

    /**
    * @Description: 查询单张优惠券
    * @Param: [id]
    * @return: xmu.oomall.domain.goods.GrouponRulePo
    * @Author: Zhou Linhui
    * @Date: 2019/12/7
    */
    GrouponRulePo findById(Integer id);

    int update(GrouponRulePo grouponRulePo);

    int delete(GrouponRulePo grouponRulePo);

    List<GrouponRulePo> findGrouponRulePos(Integer page, Integer limit);

    List<GrouponRulePo> adminFindGrouponRulePos(Integer page, Integer limit);
}
