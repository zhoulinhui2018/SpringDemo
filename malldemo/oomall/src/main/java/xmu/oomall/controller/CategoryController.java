package xmu.oomall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xmu.oomall.domain.goods.GoodsCategory;
import xmu.oomall.domain.goods.GroupOnRule;
import xmu.oomall.service.impl.GoodsServiceImpl;
import xmu.oomall.service.impl.GroupOnRuleService;
import xmu.oomall.util.ResponseUtil;

import java.util.List;

@RestController
@RequestMapping("")
public class CategoryController {
    @Autowired
    private GoodsServiceImpl goodsService;

    /**
     * @Description: 获得所有分类
     * @Param: []
     * @return: List<GoodsCategory>
     * @Author: Zhang Yaqing
     * @Date: 2019/12/7
     */
    @GetMapping("/categories")
    public List<GoodsCategory> getAllGoodsCategories()
    {
        List<GoodsCategory> allGoodsCategories=goodsService.getAllGoodsCategories();
        return allGoodsCategories;
    }

    /**
     * @Description: 新建一个分类
     * @Param: GoodsCategory
     * @return: void
     * @Author: Zhang Yaqing
     * @Date: 2019/12/7
     */
    @PostMapping("/categories")
    public void addNewCategory(GoodsCategory goodsCategory)
    {
        goodsService.addNewCategory(goodsCategory);
    }
}
