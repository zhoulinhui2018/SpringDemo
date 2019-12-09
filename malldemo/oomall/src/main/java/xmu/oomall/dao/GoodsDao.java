package xmu.oomall.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import xmu.oomall.domain.goods.Goods;
import xmu.oomall.domain.goods.GoodsCategory;
import xmu.oomall.mapper.GoodsMapper;
import xmu.oomall.util.Config;

import java.util.List;

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
    public Integer addGoods(Goods good)
    {
        return goodsMapper.addGoods(good);
    }

    public Integer deleteGoodsById(Integer id){
        return goodsMapper.deleteGoodsbyId(id);
    }


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

    /**
     * 获取所有一级商品分类
     * @param
     * @return List<GoodsCategory>
     */
    public List<GoodsCategory> getFirstLevelCategories()
    {
        List<GoodsCategory> FirstLevelCategories=goodsMapper.getFirstLevelCategories();
        return FirstLevelCategories;
    }

    /**
     * 根据id获取某分类
     * @param
     * @return GoodsCategory
     */
    public GoodsCategory getCategoryById(Integer id)
    {
        GoodsCategory goodsCategory=goodsMapper.getCategoryById(id);
        return goodsCategory;
    }

    /**
     * 修改某分类
     * @param goodsCategory
     * @return
     */
    public void updateCategory(GoodsCategory goodsCategory)
    {
        goodsMapper.updateCategory(goodsCategory);
    }

    /**
     * 删除某个分类
     * @param id
     * @return
     */
    public void deleteCategoryById(Integer id)
    {
        goodsMapper.deleteCategoryById(id);
    }
}
