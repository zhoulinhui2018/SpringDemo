package xmu.oomall.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;
import xmu.oomall.domain.goods.Brand;
import java.util.List;

@Service
@Mapper
public interface BrandMapper {
    public void addBrand(Brand newbrand);

    public int updateBrand(Brand newbrand);

    public int deleteBrand(Brand newbrand);

    public Brand findBrandById(Integer id);

    /**
     * 获取品牌列表
     * @param
     * @return 品牌列表
     */
    public List<Brand> listBrandByCondition(Brand newbrand);
}