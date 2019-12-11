package xmu.oomall.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import xmu.oomall.domain.goods.Brand;
import xmu.oomall.mapper.BrandMapper;
import xmu.oomall.domain.goods.Goods;
import xmu.oomall.mapper.GoodsMapper;


import java.util.ArrayList;
import java.util.List;

@Repository
public class BrandDao {
    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    public void addBrand(Brand newbrand){brandMapper.addBrand(newbrand);}

    public int updateBrand(Brand newbrand)
    {
        return brandMapper.updateBrand(newbrand);
    }

    public int deleteBrand(Brand newbrand)
    {
        List<Goods> goodslist= new ArrayList<Goods>();
        goodslist = goodsMapper.getGoodList();
        for(Goods goods:goodslist){
            if(goods.getBrandId().equals(newbrand.getId()))
            {
                goods.setBrandId(0);
                goodsMapper.updateGoodsById(goods);
            }
        }
        return brandMapper.deleteBrand(newbrand);
    }

    public Brand findBrandById(Integer id){return brandMapper.findBrandById(id);}

    public List<Brand> listBrandByCondition(Brand newbrand){return brandMapper.listBrandByCondition(newbrand);}

    public void setGoodsMapper(GoodsMapper goodsMapper) {
        this.goodsMapper = goodsMapper;
    }
}
