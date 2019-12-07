package xmu.oomall.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import xmu.oomall.domain.goods.Goods;
import xmu.oomall.domain.goods.Product;
import xmu.oomall.domain.goods.Promotion;
import xmu.oomall.domain.goods.PromotionPo;
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
    public Integer addGoods(Goods good)
    {
        return goodsMapper.addGoods(good);
    }

    public Integer deleteGoodsById(Integer id){
        return goodsMapper.deleteGoodsbyId(id);
    }



}
