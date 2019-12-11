package xmu.oomall.discount.service.Impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmu.oomall.discount.dao.CartItemDao;
import xmu.oomall.discount.domain.CartItem;
import xmu.oomall.discount.service.ICartItemService;

import java.math.BigDecimal;

@Service
public class CartItemServiceImpl implements ICartItemService {
    @Autowired
    private CartItemDao cartItemDao;

    @Override
    public CartItem findCartItemById(Integer cartId) {
        CartItem cartItem=cartItemDao.findCartItemById(cartId);
        return cartItem;
    }

    @Override
    public BigDecimal findProductPrice(Integer productId) {
        BigDecimal price=cartItemDao.findProductPrice(productId);
        return price;

    }
}
