package xmu.oomall.discount.mapper;

import org.apache.ibatis.annotations.Mapper;
import xmu.oomall.discount.domain.CartItemPo;

import java.math.BigDecimal;

@Mapper
public interface CartItemMapper {
    CartItemPo findCartItemById(Integer cartId);

    BigDecimal findProductPrice(Integer productId);
}
