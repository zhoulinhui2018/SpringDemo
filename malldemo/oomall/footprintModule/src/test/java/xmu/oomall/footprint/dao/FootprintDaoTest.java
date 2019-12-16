package xmu.oomall.footprint.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xmu.oomall.footprint.FootprintApplication;
import xmu.oomall.footprint.domain.FootprintItem;
import xmu.oomall.footprint.domain.FootprintItemPo;
import xmu.oomall.footprint.domain.GoodsPo;

import java.time.LocalDateTime;
import java.util.List;


@SpringBootTest(classes = FootprintApplication.class)
class FootprintDaoTest {
    @Autowired
    private FootprintDao footprintDao;

   @Test
   public void getUserFootprintListTest()
   {
       List<FootprintItem> userFootprintList=footprintDao.getUserFootprintList(1);
       for(int i=0;i<userFootprintList.size();++i){
           FootprintItem footprint=userFootprintList.get(i);
           System.out.println(footprint);
       }
   }

    @Test
    public void deleteFootprintTest()
    {
        boolean result=footprintDao.deleteFootprint(1);
        System.out.println(result);
    }

    @Test
    public void listFootprintByConditionTest()
    {
        List<FootprintItem> userFootprintList=footprintDao.listFootprintByCondition("张雅晴","菠萝");
        for(int i=0;i<userFootprintList.size();++i){
            FootprintItem footprint=userFootprintList.get(i);
            System.out.println(footprint);
        }
    }

    @Test
    public void addFootprintTest()
    {
        FootprintItem footprint=new FootprintItem();
        footprint.setId(10005);
        footprint.setGmtCreate(LocalDateTime.now());
        footprint.setBirthTime(LocalDateTime.now());
        footprint.setGoodsId(500005);
        footprint.setUserId(1);
        GoodsPo goodsPo=new GoodsPo();
        goodsPo.setId(500005);
        footprint.setGoodsPo(goodsPo);
        boolean result=footprintDao.addFootprint(footprint);
        System.out.println(result);
    }
}