package xmu.oomall.service;

import org.springframework.stereotype.Service;
import xmu.oomall.domain.coupon.CouponRule;

import java.util.List;

/**
 * @Author: Ming Qiu
 * @Description: 优惠卷有关的服务
 * @Date: Created in 15:47 2019/11/5
 * @Modified By:
 **/
@Service
public interface ICouponService {

    /**
     * 用id找到CouponRule对象
     * @param id
     * @return
     */
   CouponRule findCouponRuleById(Integer id);

    /**
     * 根据id更新CouponRule对象
     * @param id
     * @return
     */
   Integer updateCouponRuleById(Integer id);

    /**
     * 根据id删除CouponRule对象
     * @param id
     * @return
     */

   Integer deleteCouponRuleById(Integer id);

    /**
     * 获取CouponRule列表
     * @return
     */
   List<CouponRule> getCouponList();

    /**
     * 新增CouponRule
     * @param couponRule
     */
   void addCouponRule(CouponRule couponRule);
}
