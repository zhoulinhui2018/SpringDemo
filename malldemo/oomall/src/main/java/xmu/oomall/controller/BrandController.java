package xmu.oomall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xmu.oomall.domain.goods.Brand;
import xmu.oomall.service.impl.BrandService;
import xmu.oomall.util.ResponseUtil;
import xmu.oomall.domain.goods.Goods;

import java.util.List;


@RestController
@RequestMapping("")
public class BrandController {
    @Autowired
    private BrandService brandService;
    /**
    * @Description: 管理员添加品牌
    * @Param: [brand]
    * @Date: 2019/12/6
    */
    @PostMapping("/brands")
    public Object addBrand(@RequestBody Brand newbrand) {
//        Object error = validate(ad);
//        if (error != null) {
//            return error;
//        }
        brandService.addBrand(newbrand);
        return ResponseUtil.ok(newbrand);
    }

    /**
     * @Description: 管理员修改单个品牌的信息
     * @Param: [brand]
     * @Author: Ren tianhe
     * @Date: 2019/12/7
     */
    @PutMapping("/brands/{id}")
    public Object updateBrandById(@PathVariable Integer id,@RequestBody Brand newbrand){
//        Object error = validate(ad);
//        if (error != null) {
//            return error;
//        }
        brandService.updateBrand(newbrand);
        return ResponseUtil.ok(newbrand);
    }

    /**
     * @Description: 管理员删除单个品牌
     * @Param: [brand]
     * @Author: Ren tianhe
     * @Date: 2019/12/7
     */
    @DeleteMapping("/brands/{id}")
    public Object deleteBrandById(@PathVariable Integer id,@RequestBody Brand newbrand){
        brandService.deleteBrand(newbrand);

        return ResponseUtil.ok(newbrand);
    }

    /**
     * @Description: 获取单个品牌信息
     * @Param: [brand]
     * @Author: Ren tianhe
     * @Date: 2019/12/7
     */
    @GetMapping("/brands/{id}")
    public Object getBrandById(@PathVariable Integer id){
//        Object error = validate(ad);
//        if (error != null) {
//            return error;
//        }
        Brand newbrand = brandService.findBrandById(id);
        return ResponseUtil.ok(newbrand);
    }

    /**
     * @Description: 管理员根据条件所有品牌
     * @Param: [brand]
     * @Author: Ren tianhe
     * @Date: 2019/12/8
     */
    @GetMapping("/admin/brands")
    public List<Brand> listBrandByCondition(Brand newbrand){
        List<Brand> list = brandService.listBrandByCondition(newbrand);
        return list;
    }
}
