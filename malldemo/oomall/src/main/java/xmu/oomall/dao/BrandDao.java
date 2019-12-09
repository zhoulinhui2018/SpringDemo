package xmu.oomall.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import xmu.oomall.domain.goods.Brand;
import xmu.oomall.mapper.BrandMapper;

import java.util.List;

@Repository
public class BrandDao {
    @Autowired
    private BrandMapper brandMapper;

    public void addBrand(Brand newbrand){brandMapper.addBrand(newbrand);}

    public int updateBrand(Brand newbrand){return brandMapper.updateBrand(newbrand);}

    public int deleteBrand(Brand newbrand){return brandMapper.deleteBrand(newbrand);}

    public Brand findBrandById(Integer id){return brandMapper.findBrandById(id);}

    public List<Brand> getBrandList(){return brandMapper.getBrandList();}
}
