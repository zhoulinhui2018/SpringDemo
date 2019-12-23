package xmu.oomall.discount.domain.coupon;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Demo class NoThresholdStrategy
 *
 * @author Zhang Yaqing
 * @date 2019/12/20
 */
public class NoThresholdStrategy extends AbstractCouponStrategy {

    private BigDecimal offCash = BigDecimal.ZERO;

    @Override
    protected boolean isEnough(BigDecimal totalPrice, Integer totalQuantity) {
        return (totalPrice.compareTo(this.offCash)>=0);
    }

    @Override
    protected BigDecimal getDealPrice(BigDecimal itemPrice, BigDecimal totalPrice) {
        return  itemPrice.subtract(this.offCash.multiply(itemPrice.divide(totalPrice, 3, RoundingMode.HALF_UP)).setScale(2, RoundingMode.HALF_UP));

    }

    @Override
    protected BigDecimal getError(BigDecimal totalPrice, BigDecimal dealPrice) {
        return totalPrice.subtract(dealPrice).subtract(this.offCash);

    }
}
