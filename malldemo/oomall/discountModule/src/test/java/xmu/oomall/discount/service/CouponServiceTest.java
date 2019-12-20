package xmu.oomall.discount.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xmu.oomall.discount.DiscountApplication;
import xmu.oomall.discount.domain.coupon.CouponPo;
import xmu.oomall.discount.domain.coupon.CouponRulePo;
import xmu.oomall.discount.service.Impl.CouponServiceImpl;


@SpringBootTest(classes = DiscountApplication.class)
class CouponServiceTest {

    @Autowired
    private CouponServiceImpl couponService;


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

    @Test
    public void updateUsercouponTest(){
        int flag=couponService.updateUserCouponStatus(100001,100001);
        System.out.println("更新操作成功");
    }

    @Test
    public void deleteCouponRuleByIdTest(){
        int result=couponService.deleteCouponRuleById(100001);
        System.out.println(result);
    }
    @Test
    public void findcouponByIdTest(){
        CouponPo couponPo=couponService.findCouponById(1);
        System.out.println(couponPo);
    }

    @Test
    public void findCouponRuleTest(){

       CouponRulePo couponRulePo=couponService.findCouponRuleById(1);
        System.out.println(couponRulePo);
    }

    @Test
    public void modifiedCouponRuleNumTest(){
        int flag=couponService.modifiedCouponRuleNum(1);
        if(flag==1){
            System.out.println("领取数加1");
        }
    }

    @Test
    public void updateTest()
    {
        CouponRulePo couponRulePo=new CouponRulePo();
        couponRulePo.setId(100001);
        couponRulePo.setName("五折");
        couponRulePo.setPicUrl("aaaa");
        int result=couponService.updateCouponRuleById(couponRulePo);
        System.out.println(result);
    }
}