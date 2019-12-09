package xmu.oomall.service;

import org.springframework.stereotype.Service;
import xmu.oomall.domain.goods.Goods;
import xmu.oomall.domain.goods.GoodsCategory;

import java.util.List;

/**
 * 商品服务
 * @author: Ming Qiu
 * @date: Created in 21:03 2019/11/25
 **/
@Service
public interface IGoodsService {

    /**
          * 获取商品列表
          * @param
          * @return 商品列表
     */
     List<Goods> getGoodsList();

    /**
     * 新建商品
     * @param good
     * @return
     */
    int addGoods(Goods good);

    /**
     * 删除某个商品
     * @param id
     * @return
     */
    int deleteGoodsbyId(Integer id);

    /**
     * 获取某个商品
     * @param id
     * @return
     */
    Goods findGoodsById(Integer id);

    /**
     * 获取商品类别列表
     * @param
     * @return 商品分类列表
     */
    List<GoodsCategory> getAllGoodsCategories();

    /**
     * 新建商品类别
     * @param goodsCategory
     * @return
     */
    void addNewCategory(GoodsCategory goodsCategory);
}
