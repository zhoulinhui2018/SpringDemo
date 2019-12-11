package xmu.oomall.goods.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xmu.oomall.goods.domain.Product;
import xmu.oomall.goods.service.impl.ProductService;
import xmu.oomall.util.ResponseUtil;

import java.util.List;


@RestController
@RequestMapping("")
public class ProductController {
    @Autowired
    private ProductService productService;

    /**
     *管理员查询商品下的产品
     * @param id
     * @Author Ren tianhe
     * @return List<ProductVo>，所属该商品的产品列表
     * @Date 2019/12/11
     */
    @GetMapping("/goods/{id}/products")
    public Object listProductByGoodsId(Integer id){
        List<Product> products= productService.listProductByGoodsId(id);
        return ResponseUtil.ok(products);
    }


    /**
     * 管理员添加商品下的产品
     * @param id
     * @param product
     * @Author Ren tianhe
     * @return Product，新添加的产品信息
     * @Date 2019/12/11
     */
    @PostMapping("/goods/{id}/products")
    public Object addProductByGoodsId(@PathVariable Integer id,@RequestBody Product product){
        productService.addProductByGoodsId(product);
        return ResponseUtil.ok(product);
    }

    /**
     * 管理员修改商品下的某个产品信息
     * @param id
     * @param product
     * @Author Ren tianhe
     * @return Product，修改后的产品信息
     * @Date 2019/12/11
     */
    @PutMapping("/products/{id}")
    public Object updateProductById(@PathVariable Integer id,@RequestBody Product product){
        productService.updateProductById(product);
        return ResponseUtil.ok(product);
    }

    /**
     * 管理员删除商品下的某个产品信息
     * @param id
     * @Author Ren tianhe
     * @return 无（ResponseUtil.ok()即可）
     * @Date 2019/12/11
     */
    @DeleteMapping("/products/{id}")
    public Object deleteProductById(@PathVariable Integer id){
        productService.deleteProductById(id);
        return ResponseUtil.ok();
    }
}
