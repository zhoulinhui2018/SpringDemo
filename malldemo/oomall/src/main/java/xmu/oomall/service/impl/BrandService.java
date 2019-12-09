package xmu.oomall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xmu.oomall.dao.BrandDao;
import xmu.oomall.domain.goods.Brand;
import xmu.oomall.service.IBrandService;

import java.util.List;

@Transactional
@Service
public class BrandService implements IBrandService {
    @Autowired
    private BrandDao brandDao;


    @Override
    public void addBrand(Brand newbrand) {
        brandDao.addBrand(newbrand);
    }

    @Override
    public int updateBrand(Brand newbrand){return brandDao.updateBrand(newbrand);}

    @Override
    public int deleteBrand(Brand newbrand){return brandDao.deleteBrand(newbrand);}

    @Override
    public Brand findBrandById(Integer id){return brandDao.findBrandById(id);}

    @Override
    public List<Brand> getBrandList(){return brandDao.getBrandList();}
}
