package xmu.oomall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmu.oomall.dao.GoodsDao;
import xmu.oomall.domain.goods.Goods;
import xmu.oomall.service.IGoodsService;

import java.util.List;

/**
 * @author: Ming Qiu
 * @date: Created in 21:29 2019/11/25
 **/
@Service
public class GoodsServiceImpl implements IGoodsService {
    @Autowired
    private GoodsDao goodsDao;

    @Override
    public List<Goods> getGoodsList()
    {
        List<Goods> AllGoods=goodsDao.getGoodList();
        return AllGoods;
    }

    @Override
    public void addGoods(Goods good)
    {
        goodsDao.addGoods(good);
    }



}
