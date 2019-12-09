package xmu.oomall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmu.oomall.dao.GoodsDao;
import xmu.oomall.domain.goods.Goods;
import xmu.oomall.domain.goods.GoodsCategory;
import xmu.oomall.service.IGoodsService;

import java.util.List;

/**
 * @author: Ming Qiu
 * @date: Created in 21:29 2019/11/25
 **/
@Service
public abstract class GoodsServiceImpl implements IGoodsService {
    @Autowired
    private GoodsDao goodsDao;

    @Override
    public List<Goods> getGoodsList()
    {
        List<Goods> AllGoods=goodsDao.getGoodList();
        return AllGoods;
    }

    @Override
    public int addGoods(Goods good)
    {
        return goodsDao.addGoods(good);
    }

    @Override
    public int deleteGoodsbyId(Integer id){
        return goodsDao.deleteGoodsById(id);
    }

    @Override
    public Goods findGoodsById(Integer id){
        Goods goods=goodsDao.findGoodsById(id);
        return goods;
    }

    @Override
    public List<GoodsCategory> getAllGoodsCategories()
    {
        List<GoodsCategory> AllGoodsCategories=goodsDao.getAllGoodsCategories();
        return AllGoodsCategories;
    }

    @Override
    public void addNewCategory(GoodsCategory goodsCategory)
    {
        goodsDao.addNewCategory(goodsCategory);
    }
}
