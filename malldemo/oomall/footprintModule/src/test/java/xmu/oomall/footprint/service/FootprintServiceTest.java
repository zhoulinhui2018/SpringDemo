package xmu.oomall.footprint.service;

import com.github.pagehelper.PageHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xmu.oomall.footprint.FootprintApplication;
import xmu.oomall.footprint.domain.FootprintItem;
import xmu.oomall.footprint.domain.GoodsPo;
import xmu.oomall.footprint.service.impl.FootprintService;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest(classes = FootprintApplication.class)
public class FootprintServiceTest {

    @Autowired
    private FootprintService footprintService;

    @Test
    public void getUserFootprintListTest()
    {
        List<FootprintItem> userFootprintList=footprintService.getUserFootprintList(1,10,1);
        for(int i=0;i<userFootprintList.size();++i){
            FootprintItem footprint=userFootprintList.get(i);
            System.out.println(footprint);
        }
    }

    @Test
    public void deleteFootprintTest()
    {
        boolean result=footprintService.deleteFootprint(1);
        System.out.println(result);
    }

    @Test
    public void listFootprintByConditionTest()
    {
        List<FootprintItem> userFootprintList=footprintService.listFootprintByCondition(1,10,"张雅晴","菠萝");
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
        boolean result=footprintService.addFootprint(footprint);
        System.out.println(result);
    }

}
