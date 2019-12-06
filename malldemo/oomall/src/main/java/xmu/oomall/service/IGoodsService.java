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
          * 更新商品信息
          * @param
          * @return 商品列表
     */
     List<Goods> getGoodsList();

}
