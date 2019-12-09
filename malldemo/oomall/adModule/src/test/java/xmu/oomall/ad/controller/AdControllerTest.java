package xmu.oomall.ad.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import xmu.oomall.ad.AdApplication;
import xmu.oomall.ad.domain.Ad;
import xmu.oomall.util.JacksonUtil;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest(classes = AdApplication.class)
@AutoConfigureMockMvc
@Transactional
public class AdControllerTest {
    @Autowired
    private AdController adController;

    @Autowired
    private MockMvc mockMvc;


    @Rollback(false)
    @Test
    public void updateTest1()
    {
        Ad newAd=new Ad();
        newAd.setId(300002);
        newAd.setName("团购");
        newAd.setLink("www");
        newAd.setContent("降价团购活动");
        adController.update(newAd);
    }

    @Test
    public void test1(){
        Object object= adController.read(1);
        System.out.println(object.toString());
    }

    @Test
    public void getAllAdsTest(){
        List<Ad> allAds = (List<Ad>) adController.list();
        for (int i = 0; i < allAds.size(); i++) {
            Ad ad =  allAds.get(i);
            System.out.println(ad);
        }
    }
    /*
    由于参数变成了Object 但是强制类型转换不成功，所以这里有问题~~
     */
    @Rollback(false)
    @Test
    public void deleteAdbyIdTest(){
        Ad ad=new Ad();
        ad.setId(300002);
        ad.setLink("test");
        ad.setName("棉袄test");
        ad.setContent("棉袄大促销了");
        ad.setBeDefault(true);
        ad.setStartTime(LocalDateTime.now());
        ad.setEndTime(LocalDateTime.now());
        adController.delete(ad);
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

        String jsonString= JacksonUtil.toJson(ad);
//        String responseString = this.mockMvc.perform(post("/ads")).contentType("application/json;charset=UTF-8").content(jsonString))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType("application/json;charset=UTF-8"))
//                .andReturn().getResponse().getContentAsString();


        adController.create(ad);
        System.out.println("插入成功");
    }

}