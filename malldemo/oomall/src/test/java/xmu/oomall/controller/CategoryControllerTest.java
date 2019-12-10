package xmu.oomall.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import xmu.oomall.OoMallApplication;
import xmu.oomall.domain.goods.GoodsCategory;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest(classes = OoMallApplication.class)
@Transactional
public class CategoryControllerTest {
    @Autowired
    private CategoryController goodController;

    @Test
    public void getAllGoodsCategoriesTest() {
        List<GoodsCategory> goodsCategoryList = goodController.getAllGoodsCategories();
        for (int i = 0; i < goodsCategoryList.size(); i++) {
            GoodsCategory goodCategory = goodsCategoryList.get(i);
            System.out.println(goodCategory);
        }
    }

    @Test
    public void addNewCategoryTest() {
        GoodsCategory newGoodsCategory = new GoodsCategory();
        newGoodsCategory.setId(40030);
        newGoodsCategory.setName("数码");
        newGoodsCategory.setPid(20000);

        goodController.addNewCategory(newGoodsCategory);
        System.out.println("插入数据成功");
    }

    @Test
    public void getFirstLevelCategoriesTest() {
        System.out.println("查到的一级类目如下：");
        List<GoodsCategory> goodsCategoryList = goodController.getFirstLevelCategories();
        int i;
        for (i = 0; i < goodsCategoryList.size(); i++) {
            GoodsCategory goodCategory = goodsCategoryList.get(i);
            System.out.println(goodCategory);
        }
        System.out.println("共" + i + "条记录");
    }

    @Test
    public void getCategoryByIdTest() {
        GoodsCategory newGoodsCategory = goodController.getCategoryById(20003);
        System.out.println(newGoodsCategory);
        System.out.println("查找成功");
    }

    @Rollback(false)
    @Test
    public void updateCategoryTest() throws Exception {
        GoodsCategory newGoodsCategory = new GoodsCategory();

        newGoodsCategory.setPid(10001);
        newGoodsCategory.setName("饮品");
        newGoodsCategory.setGmtCreate(LocalDateTime.now());
        newGoodsCategory.setGmtModified(LocalDateTime.now());
        goodController.updateCategory(20008, newGoodsCategory);
        newGoodsCategory.setBeDelete(false);
        System.out.println("更新成功");
    }

    @Rollback(false)
    @Test
    public void deleteCategoryByIdTest() {
        goodController.deleteCategoryById(20009);
        System.out.println("删除成功");
    }


}
