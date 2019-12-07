package xmu.oomall.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import xmu.oomall.OoMallApplication;
import xmu.oomall.domain.goods.Goods;

import java.util.List;

@SpringBootTest(classes = OoMallApplication.class)
@Transactional
public class GoodControllerTest {
    @Autowired
    private GoodController goodController;

    @Test
    public void findAllGoodsTest() {
        List<Goods> goodsList = goodController.getAllGoods();
        for (int i = 0; i < goodsList.size(); i++) {
            Goods good= goodsList.get(i);
            System.out.println(good);
        }
    }

    @Rollback(false)
    @Test
    public void addGoodsTest()
    {
        Goods newgood=new Goods();
        newgood.setId(300099);
        newgood.setName("百褶裙");
        newgood.setDetail("好看的裙子");
        newgood.setBrandId(100001);
        newgood.setGoodsCategoryId("100001");

        goodController.addNewGoods(newgood);
        System.out.println("插入数据成功");
    }

    @Rollback(false)
    @Test
    public void deleteGoodsbyId(){
        Goods newgood=new Goods();
        newgood.setId(300001);
        newgood.setName("大衣");

        goodController.deleteGoodsbyId(newgood);
    }

}
