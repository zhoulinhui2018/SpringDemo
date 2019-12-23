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

    /**
     * 描述
     * @param newproduct 1
     */
    public void addProductByGoodsId(Product newproduct);

    /**
     * 描述
     * @param newproduct 1
     * @return int
     */
    public int updateProductById(Product newproduct);

    /**
     * 描述
     * @param id 1
     * @return int
     */
    public int deleteProductById(Integer id);

    /**
     * 描述
     * @param id 1
     * @return List<Product>
     */
    public List<Product> listProductByGoodsId(Integer id);



}
