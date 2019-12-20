package xmu.oomall.discount.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xmu.oomall.discount.DiscountApplication;
import xmu.oomall.discount.domain.GrouponRule;
import xmu.oomall.discount.domain.GrouponRulePo;
import xmu.oomall.discount.domain.GrouponRuleStrategy;
import xmu.oomall.discount.service.Impl.GroupOnRuleService;

@SpringBootTest(classes = DiscountApplication.class)
public class GrouponRuleDaoTest {
    @Autowired
    private GroupOnDao groupOnDao;

    @Autowired
    private GroupOnRuleService groupOnRuleService;

    @Test
    public void getStrategyTest(){

        GrouponRulePo grouponRulePo = null;
        try {
            grouponRulePo = groupOnDao.findById(200001);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(grouponRulePo);
        GrouponRule strategy = groupOnDao.getStrategy(grouponRulePo);
        System.out.println("strategy");
        System.out.println(strategy);
    }

    @Test
    public void test1(){

        GrouponRulePo groupOnDaoById = null;
        try {
            groupOnDaoById = groupOnDao.findById(100000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        GrouponRuleStrategy accessStrategy = groupOnRuleService.getAccessStrategy(groupOnDaoById, 41);
        System.out.println(accessStrategy.getRate());
    }
}
