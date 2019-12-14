package xmu.oomall.discount.domain;

import java.util.List;

/**
 * @Author: 数据库与对象模型标准组
 * @Description:团购规则对象
 * @Data:Created in 14:50 2019/12/11
 **/

public class GrouponRule extends GrouponRulePo {


    private List<GrouponRuleStrategy> strategy;

    public List<GrouponRuleStrategy> getStrategy() {
        return strategy;
    }

    public void setStrategy(List<GrouponRuleStrategy> strategy) {
        this.strategy = strategy;
    }

    public GrouponRule(List<GrouponRuleStrategy> strategy) {
        this.strategy = strategy;
    }

    public GrouponRule() {
    }

    @Override
    public String toString() {
        return "GrouponRule{" +
                "strategy=" + strategy +
                '}';
    }
}
