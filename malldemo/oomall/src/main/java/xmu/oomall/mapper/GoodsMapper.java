package xmu.oomall.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;
import xmu.oomall.domain.goods.Goods;
import xmu.oomall.domain.goods.GoodsCategory;
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
     * @param goods
     * @return
     */
    void addGoods(Goods goods);

    /**
     * 删除某商品
     * @param id
     * @return
     */
    void deleteGoodsbyId(Integer id);

    /**
     * 获取某商品的信息
     * @param id
     * @return goods
     */
    Goods findGoodsById(Integer id);


    /**
     * 获取商品分类列表
     * @param
     * @return List<GoodsCategory>
     */
    List<GoodsCategory> getAllGoodsCategories();

    /**
     * 获取商品分类列表
     * @param goodsCategory
     * @return
     */
    void addNewCategory(GoodsCategory goodsCategory);

    /**
     * 获取所有一级商品分类
     * @param
     * @return List<GoodsCategory>
     */
    List<GoodsCategory> getFirstLevelCategories();

    /**
     * 根据id获取某分类
     * @param id
     * @return GoodsCategory
     */
    GoodsCategory getCategoryById(Integer id);

    /**
     * 删除某分类
     * @param id
     * @return
     */
    void deleteCategoryById(Integer id);

    /**
     * 修改某分类
     * @param goodsCategory
     * @return
     */
    void updateCategory(GoodsCategory goodsCategory);
    Integer addGoods(Goods good);

    Integer deleteGoodsbyId(Integer id);
}
