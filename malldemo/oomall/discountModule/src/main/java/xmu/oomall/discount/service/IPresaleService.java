package xmu.oomall.discount.service;

import org.springframework.stereotype.Service;
import xmu.oomall.discount.domain.Order;
import xmu.oomall.discount.domain.OrderItem;
import xmu.oomall.discount.domain.Payment;
import xmu.oomall.discount.domain.Promotion.PresaleRule;

import java.util.List;

@Service
public interface IPresaleService {

    /**
     * @Description: 管理员新增预售规则
     * @Param: [presaleRule]
     * @return:
     * @Author: Zhang Yaqing
     * @Date: 2019/12/10
     */
    public void add(PresaleRule presaleRule);

    /**
     * @Description: 管理员查看某个预售规则详情
     * @Param: [id]
     * @return: PresaleRule
     * @Author: Zhang Yaqing
     * @Date: 2019/12/10
     */
    public PresaleRule findById(Integer id);

    /**
     * @Description: 管理员修改预售规则信息
     * @Param: [id, presaleRule]
     * @return:
     * @Author: Zhang Yaqing
     * @Date: 2019/12/10
     */
    public int update(PresaleRule presaleRule);

    /**
     * @Description: 管理员删除预售规则
     * @Param: [id, presaleRule]
     * @return: java.lang.Object
     * @Author: Zhang Yaqing
     * @Date: 2019/12/10
     */
    public int delete(Integer id);

    List<PresaleRule> findByGoodsId(Integer goodsId);

    boolean isPresaleOrder(OrderItem item, PresaleRule rule);

    List<Payment> getPrepayment(Order order,Integer maxPayTime);
}
