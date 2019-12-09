package xmu.oomall.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import xmu.oomall.OoMallApplication;
import xmu.oomall.domain.goods.GoodsCategory;

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
            GoodsCategory goodCategory= goodsCategoryList.get(i);
            System.out.println(goodCategory);
        }
    }

    @Test
    public void addNewCategoryTest(){
        GoodsCategory newGoodsCategory=new GoodsCategory();
        newGoodsCategory.setId(40030);
        newGoodsCategory.setName("数码");
        newGoodsCategory.setPid(20000);

        goodController.addNewCategory(newGoodsCategory);
        System.out.println("插入数据成功");
    }

}
