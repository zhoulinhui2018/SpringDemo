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
    public List<Goods> getGoodList();
    Integer getStockInDB(Integer id);

    /**
     * 更新货品对象
     * @param product 货品
     * @return 行数
     */
    int updateProduct(Product product);

    int updateGoods(Goods goods);



    /**
     * 新建商品
     * @param good
     * @return
     */
    void addGoods(Goods good);

    void deleteGoodsbyId(Integer id);
}
