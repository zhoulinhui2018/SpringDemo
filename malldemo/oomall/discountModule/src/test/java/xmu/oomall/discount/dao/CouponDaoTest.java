package xmu.oomall.discount.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xmu.oomall.discount.DiscountApplication;
import xmu.oomall.discount.domain.coupon.AbstractCouponStrategy;
import xmu.oomall.discount.domain.coupon.CouponPo;
import xmu.oomall.discount.domain.coupon.CouponRule;
import xmu.oomall.discount.domain.coupon.CouponRulePo;

import java.util.List;


@SpringBootTest(classes = DiscountApplication.class)
class CouponDaoTest {
    @Autowired
    private CouponDao couponDao;

   @Test
   public void findcouponRuleByIdTest()
   {
       CouponRulePo couponRule=couponDao.findCouponRuleById(100003);
       System.out.println(couponRule.getId()+" "+couponRule.getName());
   }

    @Test
    public void getGoodsTest() {
        CouponRulePo coupon=new CouponRulePo();
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
        CouponPo coupon=couponDao.findCouponById(1000001);
        CouponRulePo couponRule=couponDao.findCouponRuleById(coupon.getCouponRuleId());
        AbstractCouponStrategy strategy=couponDao.getStrategy(couponRule);
        System.out.println(strategy);
    }

    @Test
    public void deleteCouponRuleByIdTest()
    {
        int result=couponDao.deleteCouponRuleById(100001);
        System.out.println(result);
    }

    @Test
    public void updateTest()
    {
        CouponRulePo couponRulePo=new CouponRulePo();
        couponRulePo.setId(100001);
        couponRulePo.setName("五折");
        int result=couponDao.updateCouponRuleById(couponRulePo);
        System.out.println(result);
    }


}