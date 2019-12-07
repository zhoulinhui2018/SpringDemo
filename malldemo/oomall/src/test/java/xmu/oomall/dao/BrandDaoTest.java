package xmu.oomall.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xmu.oomall.OoMallApplication;
import xmu.oomall.domain.goods.Brand;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest(classes = OoMallApplication.class)
public class BrandDaoTest {
    @Autowired
    private  BrandDao brandDao;

    @Test
    public void updateBrandTest() {

        Brand newbrand=new Brand();
        newbrand.setName("bbab");
        newbrand.setPicUrl("new_url");
        newbrand.setGmtModified(LocalDateTime.now());
        newbrand.setGmtCreate(LocalDateTime.now());
        newbrand.setBeDeleted(true);
        newbrand.setDescribe("锤子好看");
        newbrand.setId(100001);

        brandDao.updateBrand(newbrand);

    }


}
