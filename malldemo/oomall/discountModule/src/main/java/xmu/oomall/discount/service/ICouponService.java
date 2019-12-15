package xmu.oomall.discount.service;

import org.springframework.stereotype.Service;
import xmu.oomall.discount.domain.OrderItem;
import xmu.oomall.discount.domain.coupon.CouponPo;
import xmu.oomall.discount.domain.coupon.CouponRulePo;

import java.util.List;
import java.util.Set;

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
   CouponRulePo findCouponRuleById(Integer id);

    /**
     * 根据id更新CouponRule对象
     * @param couponRule
     * @return
     */
   int updateCouponRuleById(CouponRulePo couponRule);

    /**
     * 根据id删除CouponRule对象
     * @param id
     * @return
     */

   int deleteCouponRuleById(Integer id);

    /**
     * 获取CouponRule列表
     * @return
     */
   List<CouponRulePo> getCouponList();

    /**
     * 新增CouponRule
     * @param couponRule
     */
    void addCouponRule(CouponRulePo couponRule);

    List<CouponPo> getCouponMyList(Integer userId);


    Set<CouponRulePo> getCanUsedCoupons(List<Integer> goodsIdList, Integer userId);

    CouponPo findCouponById(Integer id);

    List<OrderItem> calcDiscount(List<OrderItem> orderItems, Integer couponId);

    void addCoupon(CouponPo coupon);
}
