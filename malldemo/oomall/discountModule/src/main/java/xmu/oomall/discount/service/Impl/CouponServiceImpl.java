package xmu.oomall.discount.service.Impl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmu.oomall.discount.dao.CouponDao;
import xmu.oomall.discount.domain.OrderItem;
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
    public List<CouponRulePo> getCouponList(Integer page,Integer limit) {
        PageHelper.startPage(page,limit);
        List<CouponRulePo> couponRuleList=couponDao.getCouponList();
        return couponRuleList;
    }

    @Override
    public void addCouponRule(CouponRulePo couponRule) {
        couponDao.addCouponRule(couponRule);

    }

    @Override
    public void addCoupon(CouponPo coupon) {
        couponDao.addCoupon(coupon);
    }

    @Override
    public List<CouponPo> getCouponMyList(Integer userId,Integer page,Integer limit){
        PageHelper.startPage(page,limit);
        List<CouponPo> mylist=couponDao.getCouponMyList(userId);
        return mylist;
    }
    @Override
    public CouponPo findCouponById(Integer id){
        CouponPo coupon=couponDao.findCouponById(id);
        return coupon;
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

    /**
     * 根据优惠券规则id更改coupon的状态
     * @param id
     */
    @Override
    public int updateCouponStatus(Integer id){
        return couponDao.updateCouponStatus(id);
    }

}
