package xmu.oomall.controller;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import xmu.oomall.OoMallApplication;
import xmu.oomall.domain.Ad;

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
}
