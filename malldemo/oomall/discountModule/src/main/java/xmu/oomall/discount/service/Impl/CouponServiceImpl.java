package xmu.oomall.discount.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmu.oomall.discount.dao.CouponDao;
import xmu.oomall.discount.domain.OrderItem;
import xmu.oomall.discount.domain.OrderItemPo;
import xmu.oomall.discount.domain.coupon.CouponPo;
import xmu.oomall.discount.domain.coupon.CouponRulePo;
import xmu.oomall.discount.service.ICouponService;


import java.util.List;
import java.util.Set;

/**
 * @Author: Ming Qiu
 * @Description:优惠卷服务的实现
 * @Date: Created in 11:03 2019/11/19
 * @Modified By:
 **/
@Service
public class CouponServiceImpl implements ICouponService {
    @Autowired
    private CouponDao couponDao;

    @Override
    public CouponRulePo findCouponRuleById(Integer id) {
        CouponRulePo couponRule=couponDao.findCouponRuleById(id);
        return couponRule;
    }

    @Override
    public int updateCouponRuleById(CouponRulePo couponRule) {
        return couponDao.updateCouponRuleById(couponRule);
    }

    @Override
    public int deleteCouponRuleById(Integer id) {
        return couponDao.deleteCouponRuleById(id);
    }

    @Override
    public List<CouponRulePo> getCouponList() {
        List<CouponRulePo> couponRuleList=couponDao.getCouponList();
        return couponRuleList;
    }

    @Override
    public void addCouponRule(CouponRulePo couponRule) {
        couponDao.addCouponRule(couponRule);

    }

    @Override
    public List<CouponPo> getCouponMyList(Integer userId){
        List<CouponPo> mylist=couponDao.getCouponMyList(userId);
        return mylist;
    }
    @Override
    public CouponPo findCouponById(Integer id){
        CouponPo coupon=couponDao.findCouponById(id);
        return coupon;
    }


    @Override
    public Integer getProductId(Integer itemId){
          Integer productId=couponDao.getProductId(itemId);
          return productId;
    }

    @Override
    public Integer getGoodsId(Integer productId){
        Integer goodsId=couponDao.getGoodsId(productId);
        return goodsId;
    }

    @Override
    public Set<CouponRulePo> getCanUsedCoupons(List<Integer> goodsIdList, Integer userId){
        Set<CouponRulePo> canUsedCoupons=couponDao.getCanUsedCoupons(goodsIdList,userId);
        return canUsedCoupons;
    }

    @Override
    public List<OrderItem> calcDiscount(List<OrderItem> orderItems, Integer couponId){
        List<OrderItem> newItems=couponDao.calcDiscount(orderItems,couponId);
        return newItems;
    }

    @Override
    public void addCoupon(CouponPo coupon) {
        couponDao.addCoupon(coupon);
    }
}
