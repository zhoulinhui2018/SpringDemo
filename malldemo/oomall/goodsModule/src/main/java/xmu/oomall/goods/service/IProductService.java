package xmu.oomall.goods.service;

import org.springframework.stereotype.Service;
import xmu.oomall.goods.domain.Product;
import xmu.oomall.goods.dao.ProductDao;

import java.util.List;

/**
 * @Author: Ming Qiu
 * @Description: 商品有关的服务
 * @Date: Created in 15:47 2019/11/5
 * @Modified By:
 **/
@Service
public interface IProductService {

    public void addProductByGoodsId(Product newproduct);

    public int updateProductById(Product newproduct);


    public int deleteProductById(Integer id);


    public List<Product> listProductByGoodsId(Integer id);



}
