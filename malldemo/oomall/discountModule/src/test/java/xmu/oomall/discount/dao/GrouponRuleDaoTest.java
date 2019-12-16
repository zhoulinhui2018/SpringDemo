package xmu.oomall.discount.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xmu.oomall.discount.DiscountApplication;
import xmu.oomall.discount.domain.GrouponRule;
import xmu.oomall.discount.domain.GrouponRulePo;

@SpringBootTest(classes = DiscountApplication.class)
public class GrouponRuleDaoTest {
    @Autowired
    private GroupOnDao groupOnDao;

    @Test
    public void getStrategyTest(){
        GrouponRulePo grouponRulePo = groupOnDao.findById(200001);
        System.out.println(grouponRulePo);
        GrouponRule strategy = groupOnDao.getStrategy(grouponRulePo);
        System.out.println("strategy");
        System.out.println(strategy);
    }
}
