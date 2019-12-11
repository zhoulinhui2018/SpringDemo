package xmu.oomall.discount.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmu.oomall.discount.dao.CouponDao;
import xmu.oomall.discount.domain.coupon.Coupon;
import xmu.oomall.discount.domain.coupon.CouponRule;
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
    public CouponRule findCouponRuleById(Integer id) {
        CouponRule couponRule=couponDao.findCouponRuleById(id);
        return couponRule;
    }

    @Override
    public int updateCouponRuleById(CouponRule couponRule) {
        return couponDao.updateCouponRuleById(couponRule);
    }

    @Override
    public int deleteCouponRuleById(Integer id) {
        return couponDao.deleteCouponRuleById(id);
    }

    @Override
    public List<CouponRule> getCouponList() {
        List<CouponRule> couponRuleList=couponDao.getCouponList();
        return couponRuleList;
    }

    @Override
    public void addCouponRule(CouponRule couponRule) {
        couponDao.addCouponRule(couponRule);

    }

    @Override
    public List<Coupon> getCouponMyList(Integer userId){
        List<Coupon> mylist=couponDao.getCouponMyList(userId);
        return mylist;
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
    public Set<CouponRule> getCanUsedCoupons(List<Integer> goodsIdList, Integer userId){
        Set<CouponRule> canUsedCoupons=couponDao.getCanUsedCoupons(goodsIdList,userId);
        return canUsedCoupons;
    }
}
