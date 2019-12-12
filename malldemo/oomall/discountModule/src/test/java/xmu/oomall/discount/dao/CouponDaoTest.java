package xmu.oomall.discount.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xmu.oomall.discount.DiscountApplication;
import xmu.oomall.discount.domain.CartItem;
import xmu.oomall.discount.domain.OrderItem;
import xmu.oomall.discount.domain.coupon.AbstractCouponStrategy;
import xmu.oomall.discount.domain.coupon.Coupon;
import xmu.oomall.discount.domain.coupon.CouponRule;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.util.AssertionErrors.assertTrue;


@SpringBootTest(classes = DiscountApplication.class)
class CouponDaoTest {
    @Autowired
    private CouponDao couponDao;

   @Test
   public void findcouponRuleByIdTest()
   {
       CouponRule couponRule=couponDao.findCouponRuleById(100003);
       System.out.println(couponRule.getId()+" "+couponRule.getName());
   }

    @Test
    public void getGoodsTest() {
        CouponRule coupon=new CouponRule();
        coupon.setId(200005);
        coupon.setName("全场九折");
        coupon.setGoodsList1("100001,100002,100003");
        coupon.setGoodsList2("100004,100005,100006");

        List<Integer> goodIds= couponDao.getGoodsIdsInCouponRule(coupon);
        System.out.println(goodIds);

    }
    @Test
    public void getStrategyTest()
    {
        Coupon coupon=couponDao.findCouponById(1000001);
        CouponRule couponRule=couponDao.findCouponRuleById(coupon.getCouponRuleId());
        AbstractCouponStrategy strategy=couponDao.getStrategy(couponRule);
        System.out.println(strategy);
    }


}