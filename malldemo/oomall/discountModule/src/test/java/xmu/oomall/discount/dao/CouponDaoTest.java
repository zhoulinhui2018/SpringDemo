package xmu.oomall.discount.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xmu.oomall.discount.DiscountApplication;
import xmu.oomall.discount.domain.coupon.CouponRule;


@SpringBootTest(classes = DiscountApplication.class)
class CouponDaoTest {
    @Autowired
    private CouponDao couponDao;

   @Test
   public void findcouponRuleByIdTest()
   {
       CouponRule couponRule=couponDao.findCouponRuleById(10003);
       System.out.println(couponRule.getId()+" "+couponRule.getName());
   }

}