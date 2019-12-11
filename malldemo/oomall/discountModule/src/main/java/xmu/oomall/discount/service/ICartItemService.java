package xmu.oomall.discount.service;

import org.springframework.stereotype.Service;
import xmu.oomall.discount.domain.CartItem;

import java.math.BigDecimal;

@Service
public interface ICartItemService {

    CartItem findCartItemById(Integer cartId);

    BigDecimal findProductPrice(Integer productId);
}
