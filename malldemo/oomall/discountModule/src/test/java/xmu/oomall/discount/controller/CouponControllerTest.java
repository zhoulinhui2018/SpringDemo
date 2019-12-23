package xmu.oomall.discount.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import xmu.oomall.discount.DiscountApplication;

@SpringBootTest(classes = DiscountApplication.class)
@AutoConfigureMockMvc
@Transactional
public class CouponControllerTest {
    @Autowired
    private CouponController couponController;


//    @Rollback(false)
//    @Test
//    public void createTest()
//    {
//        CouponRulePo couponRule=new CouponRulePo();
//
//        couponRule.setName("全场买三赠一");
//        couponRule.setBrief("多买多送");
//        couponRule.setBeginTime(LocalDateTime.now());
//        couponRule.setEndTime(LocalDateTime.now());
//        couponRule.setValidPeriod(60);
//        couponRule.setTotal(1000);
//        couponRule.setGoodsList1("hhhhhhh");
//        couponRule.setGoodsList2("xixixixi");
//        couponRule.setGmtCreate(LocalDateTime.now());
//        couponRule.setGmtModified(LocalDateTime.now());
//        couponRule.setBeDeleted(false);
//        couponController.create(couponRule);
//        System.out.println("插入一条数据成功");
//    }
//
//    @Rollback(false)
//    @Test
//    public void createCouponTest(){
//        CouponPo coupon=new CouponPo();
//        coupon.setUserId(100001);
//        coupon.setCouponRuleId(100001);
//        coupon.setCouponSn("FFFFF");
//        coupon.setBeginTime(LocalDateTime.now());
//        coupon.setEndTime(LocalDateTime.now());
//        coupon.setUsedTime(LocalDateTime.now());
//        coupon.setName("九折折扣券");
//        coupon.setPicUrl("aaaaaa");
//        coupon.setGmtCreate(LocalDateTime.now());
//        coupon.setGmtModified(LocalDateTime.now());
//        coupon.setBeDeleted(false);
//        coupon.setStatusCode(false);
//        couponController.createCoupon(coupon);
//        System.out.println("插入一条数据成功");
//
//    }
//
//    @Test
//    public void readTest()
//    {
//        Object object= couponController.detail(100003);
//        System.out.println(object.toString());
//    }
//
//    @Rollback(false)
//    @Test
//    public void deleteTest()
//    {
//        couponController.delete(100005);
//        System.out.println("删除一条数据成功");
//    }
//
//    @Rollback(false)
//    @Test
//    public void updateTest()
//    {
//        CouponRulePo couponRule=new CouponRulePo();
//        couponRule.setName("全场买三赠一");
//
//        couponController.update(100005,couponRule);
//        System.out.println("更新一条数据成功");
//    }
////    @Test
////    public  void myListTest(){
////        List<CouponPo> myList=couponController.mylist(100001,1,10);
////        System.out.println(myList);
////    }
//
//    @Test
//    public void findCouponTest()
//    {
//        Object object=couponController.readCoupon(1000001);
//        System.out.println(object.toString());
//    }

//    @Test
//    public void selectlist()
//    {
//        //参数：Integer userId,List<Integer> cartItemIds
//        List<Integer> cartItemIds=new ArrayList<Integer>();
//        for(int i=1;i<6;i++){
//            cartItemIds.add(i);
//        }
//        Object object=couponController.selectlist(100001,cartItemIds);
//        System.out.println(object.toString());
//    }

//    @Test
//    public void deleteTest(){
//        Object result=couponController.delete(100001);
//        System.out.println(result);
//    }




}
