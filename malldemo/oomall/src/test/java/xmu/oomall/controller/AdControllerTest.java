package xmu.oomall.controller;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import xmu.oomall.OoMallApplication;
import xmu.oomall.domain.Ad;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest(classes = OoMallApplication.class)
@Transactional
public class AdControllerTest {
    @Autowired
    private AdController adController;

    private MockMvc mockMvc;

    @Test
    public void updateTest1()
    {
        Ad newAd=new Ad();
        newAd.setId(300002);
        newAd.setName("团购");
        newAd.setLink("www");
        newAd.setContent("降价团购活动");


    }

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
