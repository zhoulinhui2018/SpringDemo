package xmu.oomall.discount.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import xmu.oomall.discount.DiscountApplication;
import xmu.oomall.discount.domain.CartItemPo;
import xmu.oomall.discount.domain.OrderItemPo;

import java.math.BigDecimal;

@SpringBootTest(classes = DiscountApplication.class)
@AutoConfigureMockMvc
@Transactional
public class CartItemControllerTest {
    @Autowired
    private CartItemController cartItemController;

    @Test
    public void findCartItemTest()
    {
        CartItemPo cartItem=cartItemController.getCartItemById(1);
        OrderItemPo orderItem=new OrderItemPo(cartItem);
        BigDecimal price=cartItemController.getProductPrice(cartItem.getProductId());
        orderItem.setPrice(price);
        System.out.println(orderItem);

    }
    @Test
    public void findProductPrice()
    {

        BigDecimal price=cartItemController.getProductPrice(100001);
        System.out.println("该货品的价格为"+price);
    }
}
