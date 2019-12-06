package xmu.oomall.controller;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import xmu.oomall.OoMallApplication;
import xmu.oomall.domain.Ad;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest(classes = OoMallApplication.class)
@Transactional
public class AdControllerTest {
    @Autowired
    private AdController adController;

    @Test
    public void test1(){
        Ad ad=adController.getAdDetail(1);
        System.out.println("ad name"+ad.getName());
        System.out.println("ad content"+ad.getContent());
    }

    @Test
    public void getAllAdsTest(){
        List<Ad> allAds = adController.getAllAds();
        for (int i = 0; i < allAds.size(); i++) {
            Ad ad =  allAds.get(i);
            System.out.println(ad);
        }
    }

    @Rollback(false)
    @Test
    public void deleteAdbyIdTest(){
        adController.deleteAdbyId(200001);
        System.out.println("删除成功");
    }


    @Rollback(false)
    @Test
    public void addAdsTest(){
        Ad ad =new Ad();
        ad.setId(900003);
        ad.setLink("test");
        ad.setName("芒果test");
        ad.setContent("芒果大促销了");
        ad.setBeDefault(true);
        ad.setStartTime(LocalDateTime.now());
        ad.setEndTime(LocalDateTime.now());

        adController.addAds(ad);
        System.out.println("插入成功");
    }
}
