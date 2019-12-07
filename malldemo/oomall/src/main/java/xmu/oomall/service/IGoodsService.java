package xmu.oomall.service;

import org.springframework.stereotype.Service;
import xmu.oomall.domain.goods.Goods;
import xmu.oomall.domain.goods.Product;

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
    Integer addGoods(Goods good);
    Integer deleteGoodsbyId(Integer id);
}
