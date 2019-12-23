package xmu.oomall.goods.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import xmu.oomall.goods.domain.GoodsPo;
import xmu.oomall.goods.domain.Product;
import xmu.oomall.goods.mapper.ProductMapper;
import java.util.ArrayList;
import java.util.List;

/**
 * Demo class ProductDao
 *
 * @author Zhang Yaqing
 * @date 2019/12/20
 */
@Repository
public class ProductDao {
    @Autowired
    private ProductMapper productMapper;

    public void addProductByGoodsId(Product newproduct){productMapper.addProductByGoodsId(newproduct);}

    public int updateProductById(Product newproduct)
    {
        return productMapper.updateProductById(newproduct);
    }

    public int deleteProductById(Integer id)
    {
        return productMapper.deleteProductById(id);
    }

    public List<Product> listProductByGoodsId(Integer id){return productMapper.listProductByGoodsId(id);}

    public GoodsPo getGoodsPoById(Integer id) {
        return productMapper.getGoodsPoById(id);
    }
}
