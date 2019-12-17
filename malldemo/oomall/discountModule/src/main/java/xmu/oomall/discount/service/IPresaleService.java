package xmu.oomall.discount.service;

import org.springframework.stereotype.Service;
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
    void add(PresaleRule presaleRule);

    /**
     * 根据id找到预售规则
     * @param id
     * @return
     */
    PresaleRule findById(Integer id);

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
    PresaleRule isPresaleOrder(Integer goodsId);
}
