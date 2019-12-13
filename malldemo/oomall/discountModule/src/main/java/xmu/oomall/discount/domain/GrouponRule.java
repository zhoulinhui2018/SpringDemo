package xmu.oomall.discount.domain;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: 数据库与对象模型标准组
 * @Description:团购规则对象
 * @Data:Created in 14:50 2019/12/11
 **/

public class GrouponRule extends GrouponRulePo {

    public class Strategy{
        private Integer lowerBound;
        private Integer upperBound;
        private BigDecimal discountRate;

        public Integer getLowerBound() {
            return lowerBound;
        }

        public void setLowerBound(Integer lowerBound) {
            this.lowerBound = lowerBound;
        }

        public Integer getUpperBound() {
            return upperBound;
        }

        public void setUpperBound(Integer upperBound) {
            this.upperBound = upperBound;
        }

        public BigDecimal getDiscountRate() {
            return discountRate;
        }

        public void setDiscountRate(BigDecimal discountRate) {
            this.discountRate = discountRate;
        }
    }

    private List<Strategy> strategyList;

    public List<Strategy> getStrategyList() {
        return strategyList;
    }

    public void setStrategyList(List<Strategy> strategyList) {
        this.strategyList = strategyList;
    }

    @Override
    public String toString() {
        return "GrouponRule{" +
                "strategyList=" + strategyList +
                '}';
    }
}
