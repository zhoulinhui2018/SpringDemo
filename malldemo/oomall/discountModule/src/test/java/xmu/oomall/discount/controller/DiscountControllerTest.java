package xmu.oomall.discount.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import xmu.oomall.discount.DiscountApplication;
import xmu.oomall.discount.domain.GrouponRulePo;
import xmu.oomall.discount.service.Impl.GroupOnRuleService;

@SpringBootTest(classes = DiscountApplication.class)
@AutoConfigureMockMvc
@Transactional
public class DiscountControllerTest {
    @Autowired
    private DiscountController discountController;

    @Autowired
    private GroupOnRuleService groupOnRuleService;

    @Test
    public void DiscountOrderTest1() throws Exception{
        GrouponRulePo byId = groupOnRuleService.findById(200001);
        System.out.println(byId);
    }

}
