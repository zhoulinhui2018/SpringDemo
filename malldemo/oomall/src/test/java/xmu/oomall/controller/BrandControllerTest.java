package xmu.oomall.controller;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import xmu.oomall.OoMallApplication;
import xmu.oomall.domain.goods.Brand;
import xmu.oomall.util.JacksonUtil;
import java.util.List;
import java.lang.Object;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@SpringBootTest(classes = OoMallApplication.class)
@AutoConfigureMockMvc
@Transactional
public class BrandControllerTest {
    @Autowired
    private BrandController brandController;

    @Autowired
    private MockMvc mockMvc;


    //添加品牌测试
    @Rollback(false)
    @Test
    public void addbrand()
    {
        Brand newbrand=new Brand();
        newbrand.setName("Air");
        newbrand.setDescribe("好看");
        newbrand.setPicUrl("url");
        newbrand.setBeDeleted(false);
        newbrand.setGmtCreate(LocalDateTime.now());
        newbrand.setGmtModified(LocalDateTime.now());
        brandController.addBrand(newbrand);
    }

    //更新品牌信息测试
    @Rollback(false)
    @Test
    public void updatebrand()
    {
        Brand newbrand=new Brand();
        newbrand.setName("bbab");
        newbrand.setPicUrl("new_url");
        newbrand.setGmtModified(LocalDateTime.now());
        newbrand.setGmtCreate(LocalDateTime.now());
        newbrand.setBeDeleted(true);
        newbrand.setDescribe("锤子好看");
        newbrand.setId(100001);
        System.out.println(newbrand);
        brandController.updateBrandById(100001,newbrand);
    }

    //删除品牌信息测试
    @Rollback(false)
    @Test
    public void deletebrandTest()
    {
        Brand newbrand = new Brand();
        newbrand.setId(200007);
        brandController.deleteBrandById(200007,newbrand);
    }

    //根据id查找某个品牌信息测试
    @Test
    public void findBrandByIdTest()
    {
        System.out.println(brandController.getBrandById(100001));
    }

    //根据id查看所有品牌
    @Test
    public void getBrandList(){
        Brand ba = new Brand();
        List<Brand> b = brandController.listBrandByCondition(ba);
        for(Brand br :b){
            System.out.println(br);
        }
    }

}
