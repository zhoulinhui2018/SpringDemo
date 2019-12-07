package xmu.oomall.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import xmu.oomall.domain.goods.*;
import xmu.oomall.mapper.GoodsMapper;
import xmu.oomall.util.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 商品Dao
 * @author: Ming Qiu
 * @date: Created in 21:31 2019/11/25
 **/
@Repository
public class GoodsDao {
//    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private Config config;

    public Integer updateGoodsbyId(Goods onegoods){
        goodsMapper.updateGoods(onegoods);
        return 1;
    }
    /**
     * 获取商品列表
     * @param
     * @return List<Goods>
     */
    public List<Goods> getGoodList()
    {
        List<Goods> AllGoods=goodsMapper.getGoodList();
        return AllGoods;

    }
    /**
     * 新增商品
     * @param good
     * @return
     */
    public void addGoods(Goods good)
    {
        goodsMapper.addGoods(good);
    }

    public void deleteGoodsById(Integer id){goodsMapper.deleteGoodsbyId(id);}


    /**
     * 获取某个商品详细信息
     * @param id
     * @return
     */
    public Goods findGoodsById(Integer id){
        Goods goods=goodsMapper.findGoodsById(id);
        return goods;
    }

    /**
     * 获取商品分类列表
     * @param
     * @return List<GoodsCategory>
     */
    public List<GoodsCategory> getAllGoodsCategories()
    {
        List<GoodsCategory> AllGoodsCategory=goodsMapper.getAllGoodsCategories();
        return AllGoodsCategory;
    }

    /**
     * 新建商品分类
     * @param goodsCategory
     * @return
     */
    public void addNewCategory(GoodsCategory goodsCategory)
    {
        goodsMapper.addNewCategory(goodsCategory);
    }
}
