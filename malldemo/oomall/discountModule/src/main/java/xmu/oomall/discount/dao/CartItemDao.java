package xmu.oomall.discount.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import xmu.oomall.discount.domain.CartItem;
import xmu.oomall.discount.mapper.CartItemMapper;

import java.math.BigDecimal;


@Repository
public class CartItemDao {
    @Autowired
    private CartItemMapper cartItemMapper;

    public CartItem findCartItemById(Integer cartId) {
        CartItem cartItem=cartItemMapper.findCartItemById(cartId);
        return cartItem;
    }

    public BigDecimal findProductPrice(Integer productId) {
        BigDecimal price=cartItemMapper.findProductPrice(productId);
        return price;
    }
}
