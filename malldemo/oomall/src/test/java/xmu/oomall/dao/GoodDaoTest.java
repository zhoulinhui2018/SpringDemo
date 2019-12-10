package xmu.oomall.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xmu.oomall.OoMallApplication;
import xmu.oomall.domain.goods.Goods;
import xmu.oomall.domain.goods.GoodsCategory;

import java.util.List;

@SpringBootTest(classes = OoMallApplication.class)
public class GoodDaoTest {
    @Autowired
    private GoodsDao goodDao;

    @Test
    public void findAllGoodsTest()
    {
        List<Goods> goodsList=goodDao.getGoodList();
        for(int i=0;i<goodsList.size();i++)
        {
            Goods good=goodsList.get(i);
            System.out.println(good);
        }
    }
    @Test
    public void addGoodsTest()
    {
        Goods newgood=new Goods();
        newgood.setId(300006);
        newgood.setName("百褶裙");
        newgood.setDetail("好看的裙子");
        newgood.setBrandId(100001);
        newgood.setGoodsCategoryId("100001");
        goodDao.addGoods(newgood);
        System.out.println("插入一条数据成功");
    }
    @Test
    public void findGoodsByIdTest()
    {
        Goods goods=goodDao.findGoodsById(300099);
        System.out.println(goods);
    }

    @Test
    public void getSecondLevelCategoriesTest(){
        List<GoodsCategory> goodsCategoryList = goodDao.getSecondLevelCategories(10001);
        int i;
        for (i = 0; i < goodsCategoryList.size(); i++) {
            GoodsCategory goodCategory = goodsCategoryList.get(i);
            System.out.println(goodCategory);
        }
        System.out.println("共" + i + "条记录");
    }

    @Test
    public void getGoodsByCategoryIdTest()
    {
        List<Goods> goodsList=goodDao.getGoodsByCategoryId(100001);
        int i;
        for (i = 0; i < goodsList.size(); i++) {
            Goods goodCategory = goodsList.get(i);
            System.out.println(goodCategory);
        }
        System.out.println("共" + i + "条记录");
    }
}
