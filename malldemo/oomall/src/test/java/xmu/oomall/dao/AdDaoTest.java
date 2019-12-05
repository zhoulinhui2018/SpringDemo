package xmu.oomall.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xmu.oomall.OoMallApplication;
import xmu.oomall.domain.Ad;

import java.util.List;

@SpringBootTest(classes = OoMallApplication.class)
public class AdDaoTest {
    @Autowired
    private  AdDao adDao;
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
}
