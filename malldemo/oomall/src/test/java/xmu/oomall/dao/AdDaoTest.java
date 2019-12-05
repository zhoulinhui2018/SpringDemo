package xmu.oomall.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xmu.oomall.OoMallApplication;
import xmu.oomall.domain.Ad;

@SpringBootTest(classes = OoMallApplication.class)
public class AdDaoTest {
    @Autowired
    private  AdDao adDao;
    @Test
    public void test(){
        Ad adById = adDao.findAdById(1);
        System.out.println(adById);
    }
}
