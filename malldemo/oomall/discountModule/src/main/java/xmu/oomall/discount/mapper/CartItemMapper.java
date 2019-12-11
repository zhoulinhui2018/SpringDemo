package xmu.oomall.discount.mapper;

import org.apache.ibatis.annotations.Mapper;
import xmu.oomall.discount.domain.CartItem;

import java.math.BigDecimal;

@Mapper
public interface CartItemMapper {
    CartItem findCartItemById(Integer cartId);

    BigDecimal findProductPrice(Integer productId);
}
