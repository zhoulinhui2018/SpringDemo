package xmu.oomall.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import xmu.oomall.OoMallApplication;
import xmu.oomall.domain.goods.Brand;
@SpringBootTest(classes = OoMallApplication.class)
public class BrandDaoTest {
    @Autowired
    private  BrandDao brandDao;

    @Rollback(false)
    @Test
    public void deleteTest(){
        Brand b=new Brand();
        b.setId(100007);
        brandDao.deleteBrand(b);
    }

}
