package xmu.oomall.discount.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import xmu.oomall.discount.domain.coupon.CouponRule;
import xmu.oomall.discount.mapper.CouponMapper;


import java.util.List;

/**
 * @Author: Xu Huangchao
 * @Description: 优惠卷的DAO
 * @Date: Created in 17:02 2019/12/7
 * @Modified By:
 **/

@Repository
public class CouponDao {
    @Autowired
    private CouponMapper couponMapper;

    public void addCouponRule(CouponRule couponRule) {
        couponMapper.addCouponRule(couponRule);
    }


    public List<CouponRule> getCouponList() {
        List<CouponRule> couponRuleList=couponMapper.getCouponList();
        return couponRuleList;
    }

    public Integer deleteCouponRuleById(Integer id) {
        return couponMapper.deleteCouponRuleById(id);
    }

    public Integer updateCouponRuleById(CouponRule couponRule) {
        return couponMapper.updateCouponRuleById(couponRule);
    }

    public CouponRule findCouponRuleById(Integer id) {
        CouponRule couponRule=couponMapper.findCouponRuleById(id);
        return couponRule;
    }
}
