package xmu.oomall.discount.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import xmu.oomall.discount.domain.Order;
import xmu.oomall.discount.domain.OrderItem;
import xmu.oomall.discount.domain.Payment;
import xmu.oomall.discount.domain.Promotion.PresaleRule;
import xmu.oomall.discount.mapper.PresaleMapper;

import javax.persistence.criteria.CriteriaBuilder;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PresaleDao {
    @Autowired
    private PresaleMapper presaleMapper;

    public void add(PresaleRule presaleRule){
        presaleMapper.add(presaleRule);
    }

    public PresaleRule findById(Integer id){
        return presaleMapper.findPresaleRuleById(id);
    }

    public int update(PresaleRule presaleRule){
        return presaleMapper.updateById(presaleRule);
    }

    public int delete(Integer id){
        return presaleMapper.deleteById(id);
    }

    public List<PresaleRule> findByGoodsId(Integer goodsId) {
        return presaleMapper.findByGoodsId(goodsId);
    }


    public List<BigDecimal> presalePayment(Order order, Integer maxPayTime) {
        BigDecimal totalDeposit = BigDecimal.ZERO;
        BigDecimal totalFinalPay = BigDecimal.ZERO;
        LocalDateTime now = LocalDateTime.now();
        /**
         * 判断是否只有一条明细,超出一条明细说明是错误的预售订单
         * 这里的问题是Order->Orderitem->Product->Goods
         */
        List<BigDecimal> priceList=new ArrayList<>(2);
        if(order.getOrderItemList().size()!=1){
            return null;
        }
        else{
            OrderItem item=order.getOrderItemList().get(0);
            PresaleRule presaleRule=item.getProduct().getGoods().getPresaleRule();
            //这里的问题是明细只有一条，但是number可以不只为1，定金*number
            totalDeposit=presaleRule.getDeposit().multiply(BigDecimal.valueOf(item.getNumber()));
            totalFinalPay=presaleRule.getFinalPayment().multiply(BigDecimal.valueOf(item.getNumber()));
            priceList.add(totalDeposit);
            priceList.add(totalFinalPay);
            return priceList;
        }

    }


    /**
     * 返回定金和尾款
     * @param order
     * @param maxPayTime
     * @return List<Integer>
     */
    public List<BigDecimal> getDepositAndFinalPay(Order order, Integer maxPayTime) {
        PresaleRule prePay=new PresaleRule();

        List<BigDecimal> priceList=this.presalePayment(order,maxPayTime);
        return priceList;
    }
}
