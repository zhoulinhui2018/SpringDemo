package xmu.oomall.discount.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;
import xmu.oomall.discount.domain.coupon.CouponPo;
import xmu.oomall.discount.domain.coupon.CouponRule;
import xmu.oomall.discount.domain.coupon.CouponRulePo;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: XuHuangchao
 * @Description:优惠卷Mapper接口
 * @Date: Created in 11:03 2019/12/8
 * @Modified By:
 **/

@Mapper
@Service
public interface CouponMapper {

//    /**
//     *
//     * @param userId
//     * @param showType
//     * @return
//     */
//    List<CouponPo> showTypeCouponList(Integer userId,Integer showType);


    /**
     * 用id找优惠卷规则
     * @param id 优惠卷规则id
     * @return 优惠
     */
    CouponRulePo findCouponRuleById(Integer id);

    /**
     * 管理员增加优惠券
     * @param couponRule 1
     */
    void addCouponRule(CouponRulePo couponRule);

    /**
     * 获取优惠劵规则列表
     * @return List<CouponRule>
     */
    List<CouponRulePo> getCouponList();

    /**
     * 删除优惠券
     * @param id 1
     * @return 1
     */
    int deleteCouponRuleById(Integer id);

    /**
     * 更新优惠券信息
     * @param couponRule 1
     * @return 1
     */
    int updateCouponRuleById(CouponRulePo couponRule);

    /**
     * 更新优惠券信息
     * @param userId 1
     * @return 1
     */
    List<CouponPo> getCouponMyList(Integer userId);

    /**
     * 更新优惠券信息
     * @param couponRuleId 1
     * @return 1
     */
    CouponRule getCouponRule(Integer couponRuleId);

    /**
     * 更新优惠券信息
     * @param id 1
     * @return 1
     */
    CouponPo findCouponById(Integer id);

    /**
     * 更新优惠券信息
     * @param coupon 1
     * @return 1
     */
    int addCoupon(CouponPo coupon);

    /**
     * 根据couponRuleId更改coupon状态
     * @param id 1
     * @return 1
     */
    int updateCouponStatus(Integer id);

    /**
     * 找到用户端可显示的优惠券规则
     * @return CouponRulePo
     */
    List<CouponRulePo> findCouponRulesAvailable();

    /**
     * 用户使用优惠券后更改状态
     * @param couponPo 1
     * @return 1
     */
    int updateUserCouponStatus(CouponPo couponPo);

    /**
     * 用户使用优惠券后更改状态
     * @param couponRuleId 1
     * @return 1
     */
    int deleteAllCoupons(Integer couponRuleId);

    /**
     * 用户使用优惠券后更改状态
     * @param couponRuleId 1
     * @return 1
     */
    int modifiedCouponRuleNum(Integer couponRuleId);

    /**
     * 用户使用优惠券后更改状态
     * @param couponRuleId 1
     * @return 1
     */
    int invalidate(Integer couponRuleId);

    /**
     * 用户使用优惠券后更改状态
     * @param couponRuleId 1
     * @return CouponPo
     */
    List<CouponPo> getCouponsByRuleId(Integer couponRuleId);
    /**
     * 用户使用优惠券后更改状态
     * @param id 1
     * @return 1
     */
    int invalidateCoupon(Integer id);
    /**
     * 用户使用优惠券后更改状态
     * @param couponRuleId 1
     * @param time 1
     * @return 1
     */
    int updateCouponRuleDeleteTime(Integer couponRuleId, LocalDateTime time);
    /**
     * 用户使用优惠券后更改状态
     * @param couponRuleId 1
     * @param time 1
     * @return 1
     */
    int updateCouponDeleteTime(Integer couponRuleId, LocalDateTime time);

}
