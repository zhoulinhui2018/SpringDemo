package xmu.oomall.discount.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmu.oomall.discount.dao.PresaleDao;
import xmu.oomall.discount.domain.Order;
import xmu.oomall.discount.domain.OrderItem;
import xmu.oomall.discount.domain.Payment;
import xmu.oomall.discount.domain.Promotion.PresaleRule;
import xmu.oomall.discount.service.IPresaleService;

import java.util.List;

@Service
public class PresaleServiceImpl implements IPresaleService {
    @Autowired
    private PresaleDao presaleDao;

    @Override
    public void add(PresaleRule presaleRule) {
        presaleDao.add(presaleRule);
    }

    @Override
    public PresaleRule findById(Integer id) {
        return presaleDao.findById(id);
    }

    @Override
    public int delete(Integer id) {
        return presaleDao.delete(id);
    }

    @Override
    public int update(PresaleRule presaleRule) {
        return presaleDao.update(presaleRule);
    }

    @Override
    public List<PresaleRule> findByGoodsId(Integer goodsId){
        return presaleDao.findByGoodsId(goodsId);
    }

    @Override
    public boolean isPresaleOrder(OrderItem item, PresaleRule rule){
        return presaleDao.isPresaleOrder(item,rule);
    }

    @Override
    public List<Payment> getPrepayment(Order order,Integer maxPayTime){
        return presaleDao.getPrepayment(order,maxPayTime);
    }
}
