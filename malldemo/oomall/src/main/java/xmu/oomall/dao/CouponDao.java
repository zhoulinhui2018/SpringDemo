package xmu.oomall.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import xmu.oomall.domain.coupon.CouponRulePo;
import xmu.oomall.domain.goods.Coupon;
import xmu.oomall.domain.goods.CouponRule;
import xmu.oomall.mapper.CouponMapper;

/**
 * @Author: Ming Qiu
 * @Description: 优惠卷的DAO
 * @Date: Created in 17:02 2019/11/5
 * @Modified By:
 **/

@Repository
public class CouponDao {

//    @Autowired
//    private CouponMapper couponMapper;
//
//    public Coupon findCouponById(Integer id) {
//        Coupon coupon = couponMapper.findCouponById(id);
//        CouponRulePo couponRulePo = couponMapper.findCouponRuleById(coupon.getCouponRuleId());
//        CouponRule couponRule = new CouponRule(couponRulePo);
////        coupon.setCouponRule(couponRule);
//        return coupon;
//    }

}
