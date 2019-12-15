package xmu.oomall.ad.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xmu.oomall.ad.AdApplication;
import xmu.oomall.ad.domain.Ad;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest(classes = AdApplication.class)
public class AdDaoTest {
    @Autowired
    private  AdDao adDao;

    @Test
    public void updateAdTest() {

        Ad ad = new Ad();
        ad.setId(300001);
        ad.setName("团购");
        ad.setLink("www");
        ad.setContent("降价大团购");
        adDao.updateAdById(ad);

    }

    @Test
        public void test(){
        Ad adById = adDao.findAdById(1);
        System.out.println(adById);
    }

    @Test
    public void findAdsDefaultTest(){
        List<Ad> adsDefault = adDao.findAdsDefault();
        for (int i = 0; i < adsDefault.size(); i++) {
            Ad ad =  adsDefault.get(i);
            System.out.println(ad);
        }
    }

    @Test
    public void addAdsTest(){
        Ad ad =new Ad();
        ad.setId(900001);
        ad.setLink("test");
        ad.setName("芒果test");
        ad.setContent("芒果大促销了");
        ad.setBeDefault(true);
        ad.setStartTime(LocalDateTime.now());
        ad.setEndTime(LocalDateTime.now());
        adDao.addAds(ad);
        System.out.println("插入成功");
    }

    @Test
    public void deleteAdbyIdTest(){
        adDao.deleteAdbyId(200005);
        System.out.println("删除成功");
    }

}
