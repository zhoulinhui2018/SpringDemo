package xmu.oomall.controller;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import xmu.oomall.OoMallApplication;
import xmu.oomall.domain.Ad;
import xmu.oomall.util.JacksonUtil;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = OoMallApplication.class)
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
        Ad ad= (Ad) adController.read(1);
        System.out.println("ad name"+ad.getName());
        System.out.println("ad content"+ad.getContent());
    }

    @Test
    public void getAllAdsTest(){
        Object object=adController.list();
        System.out.println(object.toString());
    }

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
