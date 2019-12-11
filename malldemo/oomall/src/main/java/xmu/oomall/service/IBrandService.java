package xmu.oomall.service;

import org.springframework.stereotype.Service;
import xmu.oomall.domain.goods.Brand;

import java.util.List;

@Service
public interface IBrandService {
    /**
    * @Description: 新建一个品牌信息
    * @Param: [brand]
    * @Author: Ren Tianhe
    * @Date: 2019/12/6
    */

    public void addBrand(Brand newbrand);

    public int updateBrand(Brand newbrand);

    public int deleteBrand(Brand newbrand);

    public Brand findBrandById(Integer id);

    public List<Brand> listBrandByCondition(Brand newbrand);

}
