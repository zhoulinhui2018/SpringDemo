package xmu.oomall.discount.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xmu.oomall.discount.DiscountApplication;
import xmu.oomall.discount.controller.vo.PresaleRuleVo;
import xmu.oomall.discount.domain.Promotion.PresaleRule;

import java.util.List;


@SpringBootTest(classes = DiscountApplication.class)
class PresaleDaoTest {
    @Autowired
    private PresaleDao presaleDao;

   @Test
   public void findByIdTest()
   {
       PresaleRuleVo presaleRuleVo=presaleDao.findById(100001);
       System.out.println(presaleRuleVo);
   }

   @Test
    public void add()
   {
       PresaleRule presaleRule=new PresaleRule();
       presaleRule.setStatusCode(true);
       presaleRule.setGoodsId(1);
       System.out.println(presaleDao.add(presaleRule));
   }



}