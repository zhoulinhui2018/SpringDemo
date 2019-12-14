package xmu.oomall.discount.domain;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: 数据库与对象模型标准组
 * @Description:分享规则对象
 * @Data:Created in 14:50 2019/12/11
 **/

public class ShareRule extends ShareRulePo {
    private class Strategy{
        private Integer lowerBound;
        private Integer upperBound;
        private BigDecimal discountRate;
    }
    private List<Strategy> strategyList;
}
