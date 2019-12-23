package xmu.oomall.goods.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;
import xmu.oomall.goods.domain.GoodsPo;
import xmu.oomall.goods.domain.Product;

import java.util.List;

/**
 * Demo class ProductMapper
 *
 * @author Zhang Yaqing
 * @date 2019/12/20
 */
@Service
@Mapper
public interface ProductMapper {

    /**添加商品下的product
     *
     * @param newProduct 新产品
     */
    public void addProductByGoodsId(Product newProduct);

    /**更新product
     * @param newProduct 新产品
     * @return int
     */
    public int updateProductById(Product newProduct);

    /**删除product
     *
     * @param id 产品id
     * @return int
     */
    public int deleteProductById(Integer id);

    /**查看某个商品下的product_list
     *
     * @param id 产品id
     * @return List<Product>
     */
    public List<Product> listProductByGoodsId(Integer id);

    /**通过产品id获取goodsPo
     *
     * @param id 产品id
     * @return GoodsPo
     */
    GoodsPo getGoodsPoById(Integer id);
}