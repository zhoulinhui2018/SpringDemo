package xmu.oomall.discount.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import xmu.oomall.discount.DiscountApplication;
import xmu.oomall.discount.domain.Order;
import xmu.oomall.discount.domain.OrderItem;
import xmu.oomall.discount.domain.Promotion.PresaleRule;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = DiscountApplication.class)
@AutoConfigureMockMvc
@Transactional
public class PresaleControllerTest {
    @Autowired
    private PresaleController presaleController;


    @Rollback(false)
    @Test
    public void createTest(){
        PresaleRule presaleRule=new PresaleRule();
        presaleRule.setGoodsId(600001);
        presaleRule.setStartTime(LocalDateTime.now());
        presaleRule.setAdEndTime(LocalDateTime.now());
        presaleRule.setFinalStartTime(LocalDateTime.now());
        presaleRule.setEndTime(LocalDateTime.now());
        presaleRule.setStatusCode(false);
        presaleRule.setBeDeleted(false);
        presaleRule.setDeposit(BigDecimal.valueOf(40));
        presaleRule.setFinalPayment(BigDecimal.valueOf(200));
        presaleController.create(presaleRule);
        System.out.println(presaleRule);
    }

    @Rollback(false)
    @Test
    public void deleteTest()
    {
        Object object=presaleController.delete(100006);
        System.out.println(object.toString());

    }

    @Test
    public void detailTest(){
        Object object=presaleController.detail(100001);
        System.out.println(object.toString());
    }

    @Rollback(false)
    @Test
    public void updateTest(){
        PresaleRule rule=new PresaleRule();
        rule.setId(100001);
        rule.setDeposit(BigDecimal.valueOf(55));
        rule.setFinalPayment(BigDecimal.valueOf(145));
        Object object=presaleController.update(rule);
        System.out.println(object.toString());
    }

//    @Test
//    public void calDepositTest(){
//        Order order=new Order();
//        //address user orderItemlist couponId
//        OrderItem item=new OrderItem();
//        item.setGoodsId(600001);
//        item.setNumber(1);
//        item.setItemType(1);
//        item.setGmtCreate(LocalDateTime.now());
//        List<OrderItem> list=new ArrayList<>();
//        list.add(item);
//        order.setOrderItemList(list);
//        Object object=presaleController.calcDeposit(order);
//        System.out.println(object.toString());
//    }

    @Test
    public void getPrepaymentTest(){
        Order order=new Order();
        //address user orderItemlist couponId
        OrderItem item=new OrderItem();
        item.setGoodsId(600001);
        item.setNumber(1);
        item.setItemType(1);
        item.setGmtCreate(LocalDateTime.now());
        List<OrderItem> list=new ArrayList<>();
        list.add(item);
        order.setOrderItemList(list);
        Object object=presaleController.getPrePayment(order,30);
        System.out.println(object.toString());
    }

}
