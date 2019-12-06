package xmu.oomall.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;
import xmu.oomall.domain.goods.Goods;
import xmu.oomall.domain.goods.Product;
import xmu.oomall.domain.goods.PromotionPo;

import java.util.List;

/**
 * 商品Mapper
 * @author Ming Qiu
 */
@Mapper
@Service
public interface GoodsMapper {

    /**
     * 获取商品列表
     * @param
     * @return 商品列表
     */
    List<Goods> getGoodList();
}
