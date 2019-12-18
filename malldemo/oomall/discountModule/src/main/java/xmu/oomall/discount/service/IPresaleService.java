package xmu.oomall.discount.service;

import org.springframework.stereotype.Service;
import xmu.oomall.discount.controller.vo.PresaleRuleVo;
import xmu.oomall.discount.domain.Log;
import xmu.oomall.discount.domain.Order;
import xmu.oomall.discount.domain.OrderItem;
import xmu.oomall.discount.domain.Payment;
import xmu.oomall.discount.domain.Promotion.PresaleRule;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.BiConsumer;

@Service
public interface IPresaleService {

    /**
     * 管理员操作添加Log
     * @param log
     */
    void log(Log log);


    /**
     * 管理员增加预售规则
     * @param presaleRule
     */
    Integer add(PresaleRule presaleRule);

    /**
     * 根据id找到预售规则
     * @param id
     * @return
     */
    PresaleRuleVo findById(Integer id);

    /**
     * 管理员更新预售规则
     * @param presaleRule
     * @return
     */
    int update(PresaleRule presaleRule);

    /**
     * @Description: 管理员删除预售规则
     * @Param: [id, presaleRule]
     * @return: java.lang.Object
     * @Author: Zhang Yaqing
     * @Date: 2019/12/10
     */
    int delete(Integer id);

    /**
     * 为order创建定金和尾款的两个payment
     * @param order
     * @param rule
     * @return
     */
    List<Payment> presalePayment(Order order,PresaleRule rule);

    /**
     * 根据商品id判断是否是预售订单
     * @param goodsId
     * @return
     */
    PresaleRuleVo isPresaleOrder(Integer goodsId);

    Boolean getPresaleRuleOrders(PresaleRule presaleRule);

    List<Payment> getPaymentList(List<Order> orderList);

    void presaleRefund(List<Payment> paymentList);

    /**
     *根据商品ID找到预售规则列表
     * @param goodsId
     * @param page
     * @param limit
     * @return
     */
    List<PresaleRuleVo> findPresaleRule(Integer goodsId, Integer page, Integer limit);

    /**
     * 管理员找到所有的预售规则
     * @param page
     * @param limit
     * @return
     */
    List<PresaleRuleVo> findAllPresaleRules(Integer page,Integer limit);

    /**
     * 用户查看所有上架预售规则
     * @param page
     * @param limit
     * @return
     */
    List<PresaleRuleVo> findOnPresaleRules(Integer page, Integer limit);
}
