package xmu.oomall.discount.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xmu.oomall.discount.DiscountApplication;
import xmu.oomall.discount.dao.PresaleDao;

@SpringBootTest(classes = DiscountApplication.class)
public class PresaleServiceTest {
    @Autowired
    private PresaleDao presaleDao;
//
//    @Test
//    public void findByGoodsId(){
//       List<PresaleRule> ruleList=presaleDao.findByGoodsId(600001);
//       System.out.println(ruleList);
//    }

//    @Test
//    public void isPresaleOrder()
//    {
//        OrderItem item=new OrderItem();
//        PresaleRule ruleList
//        presaleDao.isPresaleOrder(item,rule);
//    }
}
