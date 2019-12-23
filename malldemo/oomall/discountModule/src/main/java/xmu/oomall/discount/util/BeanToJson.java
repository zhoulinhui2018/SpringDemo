package xmu.oomall.discount.util;

import xmu.oomall.discount.domain.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Demo class BeanToJson
 *
 * @author Zhang Yaqing
 * @date 2019/12/20
 */
public class BeanToJson {
    public static void main(String[] args) {
        List<CartItem> cartItems=new ArrayList<>();
        CartItem item=new CartItem();
        item.setId(1);
        item.setUserId(100001);
        item.setBeCheck(true);
        item.setNumber(2);
        item.setGmtCreate(LocalDateTime.now());
        item.setGmtModified(LocalDateTime.now());
        Product product=new Product();
        product.setId(3068);
        product.setGoodsId(274);
        product.setPrice(BigDecimal.valueOf(850.00));
        product.setSafetyStock(99);
        product.setGmtCreate(LocalDateTime.of(2019,12,19,9,39,12));
        product.setGmtCreate(LocalDateTime.of(2019,12,19,9,39,12));
        product.setBeDeleted(false);
        item.setProduct(product);
        cartItems.add(item);
        String json1=JacksonUtil.toJson(cartItems);
        System.out.println(json1);

    }
}
