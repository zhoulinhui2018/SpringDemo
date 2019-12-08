package xmu.oomall.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xmu.oomall.OoMallApplication;
import xmu.oomall.domain.coupon.CouponRule;

@SpringBootTest(classes = OoMallApplication.class)
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