package xmu.oomall.discount.service;

import org.springframework.stereotype.Service;
import xmu.oomall.discount.controller.vo.PresaleRuleVo;
import xmu.oomall.discount.domain.Log;
import xmu.oomall.discount.domain.Order;
import xmu.oomall.discount.domain.OrderItem;
import xmu.oomall.discount.domain.Payment;
import xmu.oomall.discount.domain.promotion.PresaleRule;

import java.util.List;

/**
 * Demo class IPresaleServiceImpl
 *
 * @author Zhang Yaqing
 * @date 2019/12/20
 */
@Service
public interface IPresaleServiceImpl {

    /**
     * 管理员操作添加Log
     * @param log 1
     */
    void log(Log log);


    /**
     * 管理员增加预售规则
     * @param  presaleRule 1
     * @return int
     * @author Zhang Yaqing
     * @date 2019/12/5
     */
    Integer add(PresaleRule presaleRule);

    /**
     * 根据id找到预售规则
     * @param id 1
     * @return PresaleRuleVo
     */
    PresaleRuleVo findById(Integer id);

    /**
     * 管理员更新预售规则
     * @param presaleRule 1
     * @return 1
     */
    int update(PresaleRule presaleRule);

    /**
     * 管理员删除预售规则
     * @param id id
     * @return 1
     */
    int delete(Integer id);

    /**
     * 为order创建定金和尾款的两个payment
     * @param order 1
     * @param rule 1
     * @return  List<Payment>
     */
    List<Payment> presalePayment(Order order,PresaleRule rule);

    /**
     * 根据商品id判断是否是预售订单
     * @param item 1
     * @return PresaleRuleVo
     */
    PresaleRuleVo isPresaleOrder(OrderItem item);

    /**
     * 为order创建定金和尾款的两个payment
     * @param presaleRule 1
     * @return  Boolean
     */
    Boolean dealRefund(PresaleRule presaleRule);

    /**
     * 为order创建定金和尾款的两个payment
     * @param orderList 1
     * @return  List<Payment>
     */
    List<Payment> getPaymentList(List<Order> orderList);
    /**
     * 为order创建定金和尾款的两个payment
     * @param paymentList 1
     */
    void presaleRefund(List<Payment> paymentList);

    /**
     *根据商品ID找到预售规则列表
     * @param goodsId 1
     * @param page 1
     * @param limit 1
     * @return List<PresaleRuleVo>
     */
    List<PresaleRuleVo> findPresaleRule(Integer goodsId, Integer page, Integer limit);

    /**
     * 管理员找到所有的预售规则
     * @param page 1
     * @param limit 1
     * @return List<PresaleRuleVo>
     */
    List<PresaleRuleVo> findAllPresaleGoods(Integer page,Integer limit);

    /**
     * 用户查看所有上架预售规则
     * @param page 1
     * @param limit 1
     * @return list<PresaleRuleVo
     */
    List<PresaleRuleVo> findOnPresaleRules(Integer page, Integer limit);
    /**
     * invalidate
     * @param id 1
     * @return  List<Payment>
     */
    int invalidate(Integer id);
}
