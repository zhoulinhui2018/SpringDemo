package xmu.oomall.goods.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import xmu.oomall.controller.GoodController;
import xmu.oomall.goods.GoodsApplication;
import xmu.oomall.goods.domain.Product;
import xmu.oomall.util.JacksonUtil;
import java.math.BigDecimal;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = GoodsApplication.class)
@AutoConfigureMockMvc
@Transactional
public class ProductControllerTest {
    @Autowired
    private ProductController productController;


    @Autowired
    private MockMvc mockMvc;


//    @Rollback(false)

    //管理员添加商品下的产品测试
    @Rollback(false)
    @Test
    public void addProductByGoodsId_Test(){
        Product newproduct = new Product();
        newproduct.setGoodsId(100006);
        newproduct.setBeDeleted(false);
        newproduct.setPicUrl("picurl");
        newproduct.setPrice(new BigDecimal(100));
        newproduct.setSaftyStock(100);
        newproduct.setProductIds("200006");
        newproduct.setSpecifications("specifications");
        productController.addProductByGoodsId(100006,newproduct);
    }

    //管理员修改商品下的某个产品信息
    @Rollback(false)
    @Test
    public void updateProductById_Test(){
        Product newproduct = new Product();
        newproduct.setId(300006);
        newproduct.setBeDeleted(false);
        newproduct.setPicUrl("picurl");
        newproduct.setPrice(new BigDecimal(130));
        newproduct.setSaftyStock(10000);               //更改价格和库存
        newproduct.setProductIds("200006");
        newproduct.setSpecifications("specifications");
        productController.updateProductById(100006,newproduct);
    }

    //管理员删除商品下的某个产品信息
    @Rollback(false)
    @Test
    public void deleteProductById_Test(){
        productController.deleteProductById(300006);
    }

    //管理员查询商品下的产品
    @Rollback(false)
    @Test
    public void listProductByGoodsId_Test(){
        List<Product> productList=new ArrayList<Product>();
        Object object = productController.listProductByGoodsId(100001);
        System.out.println(object.toString());
    }


}
