package xmu.oomall.discount.service;

import org.springframework.stereotype.Service;
import xmu.oomall.discount.domain.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * Demo class IGroupOnRuleServiceImpl
 *
 * @author Zhang Yaqing
 * @date 2019/12/20
 */
@Service
public interface IGroupOnRuleServiceImpl {

    /**
     * findGrouponRuleByGoodsId
     * @param page 1
     * @param limit 1
     * @param goodsId 1
     * @return List<GrouponRulePo>
     * @author Zhou Linhui
     * @date 2019/12/18
     */
    public List<GrouponRulePo> findGrouponRuleByGoodsId(Integer goodsId,Integer page,Integer limit);

    /** 
    * 判断可以插入吗
    * @param grouponRulePo 1
    * @return java.lang.Boolean
     * @author Zhou Linhui
     * @date 2019/12/18
    */ 
    Boolean canAdd(GrouponRulePo grouponRulePo);
    
    /** 
    * 将rate返回给Order
    * @param grouponRulePo 1
     *  @param rate 1
     * @author Zhou Linhui
     * @date 2019/12/18
    */ 
    public void returnBackRate(GrouponRulePo grouponRulePo, BigDecimal rate);


    /**
     * 管理员操作添加Log
     * @param log 1
     *
     */
    void log(Log log);


    /**
     * 根据GoodsId判断是否是团购订单
     * @param goodsId 1
     * @return 1
     */
    boolean isGrouponOrder(Integer goodsId);

    /**
     * 根据团购订单创建一个payment
     * @param order 1
     * @return 1
     */
    List<Payment> getGrouponPayment(Order order);

    /** 
    * 获得团购商品
     * @param grouponRulePo 1
     * @return GoodsPo
     * @author Zhou Linhui
     * @date 2019/12/18
    */ 
    GoodsPo getGrouponGoods(GrouponRulePo grouponRulePo);

    /**
    * : 获得当前团购达成的团购策略
     * @param grouponRulePo 1
     * @param grouponNumber 1
     * @return GrouponRuleStrategy
     * @author Zhou Linhui
     * @date 2019/12/18
     **/
    GrouponRuleStrategy getAccessStrategy(GrouponRulePo grouponRulePo,Integer grouponNumber);

    /** 
    * : 搜索目前时间点已完成团购
     * @return List<GrouponRulePo>
     * @author Zhou Linhui
     * @date 2019/12/18
    */ 
    List<GrouponRulePo> findFinishedGrouponRules();
    
    /**
    * : 获得某个团购成团订单
     * @param grouponRulePo 1
     * @return 1
     * @author Zhou Linhui
     * @date 2019/12/18
    */
    Integer getGrouponNum(GrouponRulePo grouponRulePo);

    /**
    * : 管理员新增团购规则
     * @param grouponRulePo 1
     * @author Zhou Linhui
     * @date 2019/12/18
    */
    void add(GrouponRulePo grouponRulePo) throws Exception;

    /**
    * : 查询单张优惠券
     * @param id 1
     * @return GrouponRulePo
     * @author Zhou Linhui
     * @date 2019/12/18
    */
    GrouponRulePo findById(Integer id) throws Exception;

    /**
     * update
     * @param grouponRulePo 1
     * @return int
     * @author Zhou Linhui
     * @date 2019/12/18
     */
    int update(GrouponRulePo grouponRulePo);

    /**
     * : delete
     * @param grouponRulePo 1
     * @return int
     * @author Zhou Linhui
     * @date 2019/12/18
     */
    int delete(GrouponRulePo grouponRulePo);

    /**
     * findGrouponRulePos
     * @param page 1
     * @param limit 1
     * @return List<GrouponRulePo>
     * @author Zhou Linhui
     * @date 2019/12/18
     */
    List<GrouponRulePo> findGrouponRulePos(Integer page, Integer limit);
    /**
     * adminFindGrouponRulePos
     * @param page 1
     * @param limit 1
     * @return List<GrouponRulePo>
     * @author Zhou Linhui
     * @date 2019/12/18
     */
    List<GrouponRulePo> adminFindGrouponRulePos(Integer page, Integer limit);
}
