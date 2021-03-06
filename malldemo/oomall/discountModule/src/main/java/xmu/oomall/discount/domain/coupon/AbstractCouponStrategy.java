package xmu.oomall.discount.domain.coupon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xmu.oomall.discount.domain.OrderItem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *  优惠卷策略的抽象類
 * @author XuHuangchao
 * @date 2019/12/11
 * */
public abstract class AbstractCouponStrategy {
    private Logger logger = LoggerFactory.getLogger(AbstractCouponStrategy.class);

    /**
     * 用于判断是否已经满足优惠门槛
     * @param totalPrice    总费用
     * @param totalQuantity 总数量
     * @return true- 已经满足， false - 不满足
     */
    protected abstract boolean isEnough(BigDecimal totalPrice, Integer totalQuantity);

    /**
     * 计算折扣后的价格
     * @param totalPrice 优惠货品的总价
     * @param itemPrice  当前货品的小计价格
     * @return 折扣后的价格
     */
    protected abstract BigDecimal getDealPrice(BigDecimal itemPrice, BigDecimal totalPrice);

    /**
     * 获得优惠金额与设定的误差
     * @param totalPrice 订单原价
     * @param dealPrice  优惠价
     * @return 误差值
     */
    protected abstract BigDecimal getError(BigDecimal totalPrice, BigDecimal dealPrice);

    /**
     * 获得优惠的费用
     * @param validItems 订单中可以用于优惠卷的明细
     * @param couponId   优惠卷Id
     * @return 更新的订单列表，包含可能因为误差拆开的明细
     */
    public List<OrderItem> cacuDiscount(List<OrderItem> validItems, int couponId) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        Integer totalQuantity = 0;
        List<OrderItem> discountItems = new ArrayList<>();
        if(validItems.size()!=0) {
            for (OrderItem item : validItems) {
                totalPrice = totalPrice.add(item.getPrice().multiply(BigDecimal.valueOf(item.getNumber())));
                totalQuantity += item.getNumber();
                discountItems.add(item);
            }
        }
        boolean enough = this.isEnough(totalPrice, totalQuantity);
        List<OrderItem> newItems = new ArrayList<>();
        BigDecimal dealTotalPrice = BigDecimal.ZERO;
        if (enough) {
            Iterator<OrderItem> iterator = discountItems.iterator();
            while (iterator.hasNext()){
                OrderItem item = iterator.next();
                BigDecimal dealPrice = this.getDealPrice(item.getPrice(), totalPrice);
                item.setDealPrice(dealPrice);
                dealTotalPrice = dealTotalPrice.add(dealPrice.multiply(BigDecimal.valueOf(item.getNumber())));
                newItems.add(item);
            }
            BigDecimal error = this.getError(totalPrice, dealTotalPrice);
            if (error.compareTo(BigDecimal.ZERO) != 0) {
                Boolean gotIt = false;
                for (OrderItem item : validItems) {
                    if (item.getNumber() == 1) {
                        BigDecimal dealPrice = item.getDealPrice();
                        item.setDealPrice(dealPrice.add(error));
                        gotIt = true;
                        break;
                    }
                }
                if (!gotIt) {
                    newItems.clear();
                    //无数量为1的明细，拆第一个
                    OrderItem item = validItems.get(0);
                    Integer quantity = item.getNumber();
                    item.setNumber(quantity - 1);
                    newItems.add(item);
                    try {
                        OrderItem newItem = (OrderItem) item.clone();
                        newItem.setNumber(1);
                        BigDecimal dealPrice = newItem.getDealPrice();
                        newItem.setDealPrice(dealPrice.add(error));
                        newItems.add(newItem);
                        //剩余明细也放进newItems里面，因为newItems是最后更新的明细
                        for(int i=1;i<validItems.size();i++)
                        {
                            newItems.add(validItems.get(i));
                        }
                    } catch (CloneNotSupportedException e) {
                        logger.error(e.getMessage(), e);
                    }
                }

            }
            return newItems;
        }
        else {
            return validItems;
        }
    }
}
