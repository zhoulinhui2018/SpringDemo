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
public interface ICouponServiceImpl {

//    public List<Coupon> showTypeCouponList(Integer page,Integer limit,Integer userId,Integer showType);


    /**
     * 管理员操作添加Log
     * @param log 1
     */
    void log(Log log);

    /**
     * 用id找到CouponRule对象
     * @param id 1
     * @return CouponRulePo
     */
   CouponRulePo findCouponRuleById(Integer id);

    /**
     * 管理员根据id更新CouponRule对象
     * @param couponRule 1
     * @return int
     */
   int updateCouponRuleById(CouponRulePo couponRule);

    /**
     * 管理员根据id删除CouponRule对象
     * @param id 1
     * @return int
     */

   int deleteCouponRuleById(Integer id);

    /**
     * 管理员获取CouponRule列表
     * @param page 1
     * @param limit 1
     * @return  List<CouponRulePo>
     */
   List<CouponRulePo> getCouponList(Integer page,Integer limit);

    /**
     * 新增CouponRule
     * @param couponRule 1
     */
    void addCouponRule(CouponRulePo couponRule);

    /**
     * 用户获取优惠券列表
     * @param userId 1
     * @return List<CouponPo>
     */
    List<CouponPo> getCouponMyList(Integer userId);


    /**
     * 获取所有可用优惠券
     * @param goodsIdList 1
     * @param userId 1
     * @return List<Coupon>
     */
    List<Coupon> getCanUsedCoupons(List<Integer> goodsIdList, Integer userId);

    /**
     * 根据id找到优惠券
     * @param id 1
     * @return CouponPo
     */
    CouponPo findCouponById(Integer id);

    /**
     * 用户领取一张优惠券
     * @param coupon 1
     * @return int
     */
    int addCoupon(CouponPo coupon);

    /**
     * 计算使用优惠券后的订单明细
     * @param orderItems 1
     * @param couponId 1
     * @return List<OrderItem>
     */
    List<OrderItem> calcDiscount(List<OrderItem> orderItems, Integer couponId);


    /**
     * 根据优惠券规则id更改coupon的状态
     * @param id 1
     * @return int
     */
    int updateCouponStatus(Integer id);


    /**
     * 使用优惠券后置成已使用
     * @param userId 1
     * @param couponId 1
     * @return int
     */
    int updateUserCouponStatus(Integer userId, Integer couponId);

    /**
     * 用户查看优惠券规则
     * @param page 1
     * @param limit 1
     * @return List<CouponRulePo>
     */
    List<CouponRulePo> getUserCouponRules(Integer page, Integer limit);

    /**
     * 优惠券领取数量加一
     * @param couponRuleId 1
     * @return int
     */
    int modifiedCouponRuleNum(Integer couponRuleId);

    /**
     * 根据不同种类划分优惠券
     * @param page 1
     * @param limit 1
     * @param userId 1
     * @param showType 1
     * @return  List<Coupon>
     */
    List<Coupon> showTypeCouponList(Integer page, Integer limit, Integer userId, Integer showType);
}
