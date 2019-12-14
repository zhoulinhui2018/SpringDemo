package xmu.oomall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xmu.oomall.domain.goods.GoodsCategory;
import xmu.oomall.service.impl.GoodsServiceImpl;
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
    public List<GoodsCategory> getAllGoodsCategories() {
        List<GoodsCategory> allGoodsCategories = goodsService.getAllGoodsCategories();
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
    public void addNewCategory(GoodsCategory goodsCategory) {
        goodsService.addNewCategory(goodsCategory);
    }

    /**
     * @Description: 查看所有一级分类
     * @Param: []
     * @return: void
     * @Author: Zhang Yaqing
     * @Date: 2019/12/7
     */
    @GetMapping("/categories/l1")
    public List<GoodsCategory> getFirstLevelCategories() {
        List<GoodsCategory> firstLevelCategories = goodsService.getFirstLevelCategories();
        return firstLevelCategories;
    }

    /**
     * @Description: 查看单个分类信息
     * @Param: id
     * @return: GoodsCategory
     * @Author: Zhang Yaqing
     * @Date: 2019/12/7
     */
    @GetMapping("/categories/{id}")
    public GoodsCategory getCategoryById(Integer id) {
        GoodsCategory goodsCategory = goodsService.getCategoryById(id);
        return goodsCategory;
    }

    /**
     * @Description: 修改某个种类
     * @Param: [id, goodsCategory]
     * @return: java.lang.Object
     * @Author: Zhang Yaqing
     * @Date: 2019/12/7
     */
    @PutMapping("/categories/{id}")
    public Object updateCategory(@PathVariable Integer id, @RequestBody GoodsCategory goodsCategory) {
        goodsService.updateCategory(goodsCategory);
        return ResponseUtil.ok(goodsCategory);
    }

    /**
     * @Description: 删除单个分类信息
     * @Param: id
     * @return:
     * @Author: Zhang Yaqing
     * @Date: 2019/12/7
     */
    @DeleteMapping("/categories/{id}")
    public Object deleteCategoryById(Integer id) {
        boolean result=goodsService.deleteCategoryById(id);
        return ResponseUtil.ok(result);
    }

}
