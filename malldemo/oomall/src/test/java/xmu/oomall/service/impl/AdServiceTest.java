package xmu.oomall.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xmu.oomall.OoMallApplication;
import xmu.oomall.domain.Ad;

@SpringBootTest(classes = OoMallApplication.class)
public class AdServiceTest {

    @Autowired
    private AdServiceImpl adService;

    @Test
    public void test(){
        System.out.println("test zhou");
        if (adService.findAdById(1)!=null){
            System.out.println(adService.findAdById(1));
        }else {
            System.out.println("查无此项");
        }
    }
    @Test
    public void updateTest()
    {
        Ad ad = new Ad();
        ad.setId(300003);
        ad.setName("团购");
        ad.setLink("www");
        ad.setContent("降价大团购");
        adService.updateAdById(ad);
    }

    @Test
    public void deleteAdbyIdTest(){
        adService.deleteAdbyId(300002);
    }

}
