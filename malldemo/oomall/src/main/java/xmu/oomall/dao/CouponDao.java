package xmu.oomall.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import xmu.oomall.domain.coupon.CouponRule;
import xmu.oomall.mapper.CouponMapper;

import java.util.List;

/**
 * @Author: Ming Qiu
 * @Description: 优惠卷的DAO
 * @Date: Created in 17:02 2019/11/5
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
        List<CouponRule> cooupon
    }

    public Integer deleteCouponRuleById(Integer id) {
    }

    public Integer updateCouponRuleById(Integer id) {
    }

    public CouponRule findCouponRuleById(Integer id) {
    }
}
