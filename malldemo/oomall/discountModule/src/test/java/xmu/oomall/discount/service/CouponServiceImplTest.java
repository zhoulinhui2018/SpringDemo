package xmu.oomall.discount.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xmu.oomall.OoMallApplication;


@SpringBootTest(classes = OoMallApplication.class)
class CouponServiceImplTest {

    @Autowired
    private ICouponService couponService;

//    @Test
//    public void findCouponById1(){
//        Coupon coupon = couponService.findCouponById(1);
//        System.out.println(coupon);
//        assertEquals(coupon.getId(), 1);
////        assertEquals(coupon.getCouponSn(), "SF0001");
//        assertEquals(coupon.getCouponRuleId(), 1);
//        assertEquals(coupon.getName(), "测试优惠卷1");
//
//        coupon = couponService.findCouponById(3);
//        assertEquals(coupon.getId(), 3);
////        assertEquals(coupon.getCouponSn(), "SF0003");
//        assertEquals(coupon.getCouponRuleId(), 2);
//        assertEquals(coupon.getName(), "测试优惠卷2");
//    }
}