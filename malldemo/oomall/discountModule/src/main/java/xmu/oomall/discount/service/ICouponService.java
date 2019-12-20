package xmu.oomall.discount.service;

import org.springframework.stereotype.Service;
import xmu.oomall.discount.domain.Log;
import xmu.oomall.discount.domain.OrderItem;
import xmu.oomall.discount.domain.coupon.Coupon;
import xmu.oomall.discount.domain.coupon.CouponPo;
import xmu.oomall.discount.domain.coupon.CouponRulePo;

import java.util.List;

/**
 * @Author: Ming Qiu
 * @Description: 优惠卷有关的服务
 * @Date: Created in 15:47 2019/11/5
 * @Modified By:
 **/
@Service
public interface ICouponService {

    public List<Coupon> getMyCoupons0(Integer page,Integer limit,Integer userId);
    public List<Coupon> getMyCoupons1(Integer page,Integer limit,Integer userId);
    public List<Coupon> getMyCoupons2(Integer page,Integer limit,Integer userId);
    public List<Coupon> getMyCoupons3(Integer page,Integer limit,Integer userId);


    /**
     * 管理员操作添加Log
     * @param log
     */
    void log(Log log);

    /**
     * 用id找到CouponRule对象
     * @param id
     * @return
     */
   CouponRulePo findCouponRuleById(Integer id);

    /**
     * 管理员根据id更新CouponRule对象
     * @param couponRule
     * @return
     */
   int updateCouponRuleById(CouponRulePo couponRule);

    /**
     * 管理员根据id删除CouponRule对象
     * @param id
     * @return
     */

   int deleteCouponRuleById(Integer id);

    /**
     * 管理员获取CouponRule列表
     * @return
     */
   List<CouponRulePo> getCouponList(Integer page,Integer limit);

    /**
     * 新增CouponRule
     * @param couponRule
     */
    void addCouponRule(CouponRulePo couponRule);

    /**
     * 用户获取优惠券列表
     * @param userId
     * @return
     */
    List<CouponPo> getCouponMyList(Integer userId,Integer page,Integer limit);


    /**
     * 获取所有可用优惠券
     * @param goodsIdList
     * @param userId
     * @return
     */
    List<Coupon> getCanUsedCoupons(List<Integer> goodsIdList, Integer userId);

    /**
     * 根据id找到优惠券
     * @param id
     * @return
     */
    CouponPo findCouponById(Integer id);

    /**
     * 用户领取一张优惠券
     * @param coupon
     */
    void addCoupon(CouponPo coupon);

    /**
     * 计算使用优惠券后的订单明细
     * @param orderItems
     * @param couponId
     * @return
     */
    List<OrderItem> calcDiscount(List<OrderItem> orderItems, Integer couponId);


    /**
     * 根据优惠券规则id更改coupon的状态
     * @param id
     */
    int updateCouponStatus(Integer id);


    /**
     * 使用优惠券后置成已使用
     * @param userId
     * @param couponId
     */
    int updateUserCouponStatus(Integer userId, Integer couponId);

    /**
     * 用户查看优惠券规则
     * @param page
     * @param limit
     * @return
     */
    List<CouponRulePo> getUserCouponRules(Integer page, Integer limit);

    /**
     * 优惠券领取数量加一
     * @param couponRuleId
     */
    int modifiedCouponRuleNum(Integer couponRuleId);
}
