package xmu.oomall.discount.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xmu.oomall.discount.domain.CartItem;
import xmu.oomall.discount.service.Impl.CartItemServiceImpl;
import xmu.oomall.discount.service.Impl.CouponServiceImpl;
import xmu.oomall.util.ResponseUtil;

import java.math.BigDecimal;

@RestController
@RequestMapping("")
public class CartItemController {
    @Autowired
    private CartItemServiceImpl cartService;

    @GetMapping("/cartItem/{id}")
    public CartItem getCartItemById(Integer cartId)
    {
        CartItem cartItem=cartService.findCartItemById(cartId);
        return cartItem;
    }

    public BigDecimal getProductPrice(Integer productId) {
        BigDecimal price=cartService.findProductPrice(productId);
        return price;
    }
}
