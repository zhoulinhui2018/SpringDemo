package xmu.oomall.goods.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xmu.oomall.goods.dao.ProductDao;
import xmu.oomall.goods.domain.Product;
import xmu.oomall.goods.service.IProductService;

import java.util.List;

@Transactional
@Service
public class ProductService implements IProductService {
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

}
