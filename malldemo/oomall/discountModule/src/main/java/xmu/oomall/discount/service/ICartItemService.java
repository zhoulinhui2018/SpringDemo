package xmu.oomall.discount.service;

import org.springframework.stereotype.Service;
import xmu.oomall.discount.domain.CartItemPo;

import java.math.BigDecimal;

@Service
public interface ICartItemService {

    CartItemPo findCartItemById(Integer cartId);

    BigDecimal findProductPrice(Integer productId);
}
