package xmu.oomall.goods.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;
import xmu.oomall.goods.domain.Product;

import java.util.List;

@Service
@Mapper
public interface ProductMapper {

    //添加商品下的product
    public void addProductByGoodsId(Product newproduct);

    //更新product
    public int updateProductById(Product newproduct);

    //删除product
    public int deleteProductById(Integer id);

    //查看某个商品下的product_list
    public List<Product> listProductByGoodsId(Integer id);

}