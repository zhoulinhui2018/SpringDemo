package xmu.oomall.ad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import xmu.oomall.ad.AdApplication;

@SpringBootTest(classes = AdApplication.class)
@AutoConfigureMockMvc
@Transactional
public class AdControllerTest {
    @Autowired
    private AdController adController;


    @Autowired
    private MockMvc mockMvc;


//    @Rollback(false)
//    @Test
//    public void updateTest1()
//    {
//        Ad newAd=new Ad();
//        newAd.setId(300002);
//        newAd.setName("团购");
//        newAd.setLink("www");
//        newAd.setContent("降价团购活动");
//        adController.adminUpdateAd(100001,newAd);
//    }
//
//    @Test
//    public void findTest1(){
//        Object object= adController.adminFindAdById(100001);
//        System.out.println(object);
//    }
//
//    @Test
//    public void adminGetAllAdsTest(){
//        List<Ad> allAds = (List<Ad>) adController.adminFindAdList(1,5,"促销","芒果");
//        for (int i = 0; i < allAds.size(); i++) {
//            Ad ad =  allAds.get(i);
//            System.out.println(ad);
//        }
//    }
//    /*
//    由于参数变成了Object 但是强制类型转换不成功，所以这里有问题~~
//     */
//    @Rollback(false)
//    @Test
//    public void deleteAdbyIdTest(){
//        adController.adminDeleteAd(100001);
//        System.out.println("删除成功");
//    }
//
//
//    @Rollback(false)
//    @Test
//    public void addAdsTest(){
//        Ad ad =new Ad();
//        ad.setLink("test");
//        ad.setName("芒果test");
//        ad.setContent("芒果大促销了");
//        String jsonString= JacksonUtil.toJson(ad);
//
//        System.out.println(adController.adminCreateAd(ad));
//
//    }
//
//
//    @Test
//    public void userFindAdsTest(){
//        Object adsList = adController.userFindAdsList(1,5);
//        System.out.println(adsList);
//    }

}
