package xmu.oomall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xmu.oomall.dao.CouponDao;
import xmu.oomall.domain.coupon.CouponRule;
import xmu.oomall.service.ICouponService;

import java.util.List;

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
    public Integer updateCouponRuleById(Integer id) {
        return couponDao.updateCouponRuleById(id);
    }

    @Override
    public Integer deleteCouponRuleById(Integer id) {
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

}
