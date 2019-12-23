package xmu.oomall.discount.domain;

import java.math.BigDecimal;

/**
 * Demo class GrouponRuleStrategy
 *
 * @author Zhang Yaqing
 * @date 2019/12/20
 */
public class GrouponRuleStrategy {
    private Integer lowerbound;
    private Integer upperbound;
    private BigDecimal rate;

    public GrouponRuleStrategy(Integer lowerbound, Integer upperbound, BigDecimal rate) {
        this.lowerbound = lowerbound;
        this.upperbound = upperbound;
        this.rate = rate;
    }

    public GrouponRuleStrategy() {
    }

    public Integer getLowerbound() {
        return lowerbound;
    }

    public void setLowerbound(Integer lowerbound) {
        this.lowerbound = lowerbound;
    }

    public Integer getUpperbound() {
        return upperbound;
    }

    public void setUpperbound(Integer upperbound) {
        this.upperbound = upperbound;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }
}
