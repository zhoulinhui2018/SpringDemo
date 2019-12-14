package xmu.oomall.discount.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import xmu.oomall.discount.domain.Order;
import xmu.oomall.discount.domain.OrderItem;
import xmu.oomall.discount.domain.Payment;
import xmu.oomall.discount.domain.Promotion.PresaleRule;
import xmu.oomall.discount.mapper.PresaleMapper;

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

    public boolean isPresaleOrder(OrderItem item, PresaleRule rule) {
        LocalDateTime startTime=rule.getStartTime();//预售规则中的开始时间
        LocalDateTime adEndTime=rule.getAdEndTime();//预售规则中预售结束时间
        LocalDateTime createTime=item.getGmtCreate();
        int type=item.getItemType();
        if(type==1&&(createTime.compareTo(adEndTime)<=0)&&(createTime.compareTo(startTime)>=0))
        {
           return true;
        }
        else return false;


    }
    public List<Payment> presalePayment(Order order,PresaleRule rule, Integer maxPayTime) {
        BigDecimal totalDeposit = BigDecimal.ZERO;
        BigDecimal totalFinalPay = BigDecimal.ZERO;
        LocalDateTime now = LocalDateTime.now();

        /**
         * 默认可以有多个预售明细，但是其实是同一种商品,每条明细都只有一个数量
         */
        for (OrderItem item: order.getOrderItemList()){
            totalDeposit= totalDeposit.add(rule.getDeposit());
            totalFinalPay = totalFinalPay.add(rule.getFinalPayment());
        }

        Payment prePayment = new Payment();
        prePayment.setActualPrice(totalDeposit);
        prePayment.setEndTime(now.plusMinutes(maxPayTime));

        Payment finalPayment = new Payment();
        finalPayment.setActualPrice(totalFinalPay);
        finalPayment.setBeginTime(rule.getFinalStartTime());
        finalPayment.setEndTime(rule.getEndTime());

        List<Payment> ret = new ArrayList<>(2);
        ret.add(prePayment);
        ret.add(finalPayment);
        return ret;
    }

    /**
     * 获取order所对应的预售规则
     * @param order
     * @return PresaleRule
     */
    public PresaleRule getCanUsedPresale(Order order){
        //获取订单中的明细列表，这些明细列表理应是同一种goods,可以有不同products
        List<OrderItem> orderItems=order.getOrderItemList();
        //那这些商品的GoodsId是相同的
        OrderItem item=orderItems.get(0);
        Integer goodId=item.getProduct().getGoodsId();

    }

    /**
     * 返回定金和尾款两个payment
     * @param order
     * @param maxPayTime
     * @return List<Payment>
     */
    public List<Payment> getPrepayment(Order order,Integer maxPayTime) {
        PresaleRule prePay=new PresaleRule();
        //这里要判断出是哪个presaleRule然后再调用presalePayment
        PresaleRule rule=new PresaleRule();

        List<Payment> payments=this.presalePayment(order,rule,maxPayTime);
        return payments;
    }
}
