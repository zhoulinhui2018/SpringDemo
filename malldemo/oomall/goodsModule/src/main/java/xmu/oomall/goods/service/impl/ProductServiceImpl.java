package xmu.oomall.goods.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xmu.oomall.goods.dao.ProductDao;
import xmu.oomall.goods.domain.GoodsPo;
import xmu.oomall.goods.domain.Product;
import xmu.oomall.goods.service.IProductService;

import java.util.List;

/**
 * Demo class ProductServiceImpl
 *
 * @author Zhang Yaqing
 * @date 2019/12/20
 */
@Transactional(rollbackFor=Exception.class)
@Service
public class ProductServiceImpl implements IProductService {
    @Autowired
    private ProductDao productDao;

    @Override
    public void addProductByGoodsId(Product newproduct){
        productDao.addProductByGoodsId(newproduct);
    }

    @Override
    public int updateProductById(Product newproduct){
        return productDao.updateProductById(newproduct);
    }


    @Override
    public int deleteProductById(Integer id){
        return productDao.deleteProductById(id);
    }

    @Override
    public List<Product> listProductByGoodsId(Integer id){
        return productDao.listProductByGoodsId(id);
    }

    public GoodsPo getGoodsPoById(Integer id) {
        return productDao.getGoodsPoById(id);
    }
}
