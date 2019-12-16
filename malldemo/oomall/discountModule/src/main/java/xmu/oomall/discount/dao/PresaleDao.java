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
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PresaleDao {
    @Autowired

    private PresaleMapper presaleMapper;

    /**
     * 管理员添加预售规则
     * @param presaleRule
     */
    public void add(PresaleRule presaleRule){
        presaleMapper.add(presaleRule);
    }

    /**
     * 管理员更新预售规则
     * @param presaleRule
     * @return
     */
    public int update(PresaleRule presaleRule){
        return presaleMapper.updateById(presaleRule);
    }

    /**
     * 管理员根据id删除预售规则
     * @param id
     * @return
     */
    public int delete(Integer id){
        return presaleMapper.deleteById(id);
    }

    /**
     * 根据id找到预售规则
     * @param id
     * @return
     */
    public PresaleRule findById(Integer id){
        return presaleMapper.findPresaleRuleById(id);
    }

    /**
     * 根据goodsId找到所有的预售规则
     * @param goodsId
     * @return
     */
    public List<PresaleRule> findByGoodsId(Integer goodsId) {
        return presaleMapper.findByGoodsId(goodsId);
    }


    /**
     * 根据order和presaleRule得出定金和尾款两个payment
     * @param order
     * @return
     */
    public List<Payment> presalePayment(Order order,PresaleRule rule) {
        BigDecimal prePay = BigDecimal.ZERO;
        BigDecimal finalPay = BigDecimal.ZERO;
        LocalDateTime now = LocalDateTime.now();

        for (OrderItem item: order.getOrderItemList()){
            prePay = prePay.add(rule.getDeposit());
            finalPay = finalPay.add(rule.getFinalPayment());
        }

        Payment prePayment = new Payment();
        prePayment.setActualPrice(prePay);
        //默认最大付款间隔是30min
        prePayment.setEndTime(now.plusMinutes(30));
        prePayment.setOrderId(order.getId());

        Payment finalPayment = new Payment();
        finalPayment.setActualPrice(finalPay);
        finalPayment.setBeginTime(rule.getFinalStartTime());
        finalPayment.setEndTime(rule.getEndTime());
        finalPayment.setOrderId(order.getId());

        List<Payment> ret = new ArrayList<>(2);
        ret.add(prePayment);
        ret.add(finalPayment);
        return ret;

    }


    public PresaleRule isPresaleOrder(Integer goodsId) {
        List<PresaleRule> presaleRuleList=presaleMapper.findByGoodsId(goodsId);
        List<PresaleRule> selectRuleList=new ArrayList<>();
        LocalDateTime nowTime=LocalDateTime.now();

        for(PresaleRule rule:presaleRuleList){
            //预售规则的付定金的时间段
            LocalDateTime beginTime=rule.getStartTime();
            LocalDateTime endTime=rule.getAdEndTime();

            if((nowTime.compareTo(beginTime)>=0)&&(nowTime.compareTo(endTime)<=0)){
                return rule;
            }
        }
        return null;
    }
}
