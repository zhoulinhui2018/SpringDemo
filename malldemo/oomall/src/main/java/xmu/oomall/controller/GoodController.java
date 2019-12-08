package xmu.oomall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import xmu.oomall.domain.goods.Goods;
import xmu.oomall.service.IGoodsService;
import xmu.oomall.service.impl.GoodsServiceImpl;

import java.util.List;

@RestController
@RequestMapping("")
public class GoodController {
    @Autowired
    private GoodsServiceImpl goodsService;


    /**
     * @Description: 用户获得商品列表
     * @Param: []
     * @return: List<Goods>
     * @Author: Xu Huangchao
     * @Date: 2019/12/6
     */
    @GetMapping("/goods")
    public Object list()
    {
       List<Goods> AllGoods=goodsService.getGoodsList();
       return AllGoods;
    }

    /**
     * @Description: 管理员新建商品
     * @Param: Goods
     * @return:
     * @Author: Xu Huangchao
     * @Date: 2019/12/6
     */
    @PostMapping("/goods")
    public Object create(@RequestBody Goods goods)
    {
        return goodsService.addGoods(goods);
    }

    @DeleteMapping("/goods/{id}")
    public Object delete(@RequestBody Goods goods){
        Integer id=goods.getId();
        return goodsService.deleteGoodsbyId(id);
    }
}
