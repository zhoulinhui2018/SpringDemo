package xmu.oomall.discount.controller;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import xmu.oomall.discount.DiscountApplication;
import xmu.oomall.discount.domain.OrderPo;

@SpringBootTest(classes = DiscountApplication.class)
@AutoConfigureMockMvc
@Transactional
public class DiscountControllerTest {
    @Autowired
    private DiscountController discountController;

//    @Test
//    public void DiscountOrderTest(){
//        OrderPo orderPo=new OrderPo();
//
//
//        discountController.discountOrder(order);
//    }

}
