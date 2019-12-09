package xmu.oomall.discount.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import xmu.oomall.discount.DiscountApplication;
import xmu.oomall.discount.domain.coupon.Coupon;
import xmu.oomall.discount.domain.coupon.CouponRule;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = DiscountApplication.class)
@AutoConfigureMockMvc
@Transactional
public class CouponControllerTest {
    @Autowired
    private CouponController couponController;

    @Rollback(false)
    @Test
    public void createTest()
    {
        CouponRule couponRule=new CouponRule();

        couponRule.setName("全场买三赠一");
        couponRule.setBrief("多买多送");
        couponRule.setBeginTime(LocalDateTime.now());
        couponRule.setEndTime(LocalDateTime.now());
        couponRule.setValidPeriod(60);
        couponRule.setTotal(1000);
        couponRule.setGoodsList1("hhhhhhh");
        couponRule.setGoodsList2("xixixixi");
        couponRule.setGmtCreate(LocalDateTime.now());
        couponRule.setGmtModified(LocalDateTime.now());
        couponRule.setBeDeleted(false);
        couponController.create(couponRule);
        System.out.println("插入一条数据成功");
    }

    @Test
    public void readTest()
    {
        Object object= couponController.read(100003);
        System.out.println(object.toString());
    }

    @Rollback(false)
    @Test
    public void deleteTest()
    {
        CouponRule couponRule=new CouponRule();
        couponRule.setId(100005);
        couponRule.setName("全场买二赠一");
        couponController.delete(couponRule);
        System.out.println("删除一条数据成功");
    }

    @Rollback(false)
    @Test
    public void updateTest()
    {
        CouponRule couponRule=new CouponRule();
        couponRule.setId(100005);
        couponRule.setName("全场买三赠一");

        couponController.update(couponRule);
        System.out.println("更新一条数据成功");
    }
    @Test
    public  void myListTest(){
        List<Coupon> myList=couponController.mylist(100001);
        System.out.println(myList);
    }

    @Test
    public void selectlist()
    {
        //参数：Integer userId,List<Integer> cartItemIds
        List<Integer> cartItemIds=new ArrayList<Integer>();
        for(int i=1;i<6;i++){
            cartItemIds.add(i);
        }
        Object object=couponController.selectlist(100001,cartItemIds);
        System.out.println(object.toString());
    }
}
