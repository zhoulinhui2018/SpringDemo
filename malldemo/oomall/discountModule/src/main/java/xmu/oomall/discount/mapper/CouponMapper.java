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
     * @param couponRule
     */
    void addCouponRule(CouponRulePo couponRule);

    /**
     * 获取优惠劵规则列表
     * @return List<CouponRule>
     */
    List<CouponRulePo> getCouponList();

    /**
     * 删除优惠券
     * @param id
     * @return
     */
    int deleteCouponRuleById(Integer id);

    /**
     * 更新优惠券信息
     * @param couponRule
     * @return
     */
    int updateCouponRuleById(CouponRulePo couponRule);

    List<CouponPo> getCouponMyList(Integer userId);


    CouponRule getCouponRule(Integer couponRuleId);

    CouponPo findCouponById(Integer id);


    int addCoupon(CouponPo coupon);

    /**
     * 根据couponRuleId更改coupon状态
     * @param id
     * @return
     */
    int updateCouponStatus(Integer id);

    /**
     * 找到用户端可显示的优惠券规则
     * @return
     */
    List<CouponRulePo> findCouponRulesAvailable();

    /**
     * 用户使用优惠券后更改状态
     * @param couponPo
     * @return
     */
    int updateUserCouponStatus(CouponPo couponPo);

    int deleteAllCoupons(Integer couponRuleId);

    int modifiedCouponRuleNum(Integer couponRuleId);

    int invalidate(Integer couponRuleId);

    List<CouponPo> getCouponsByRuleId(Integer couponRuleId);

    int invalidateCoupon(Integer id);

    int updateCouponRuleDeleteTime(Integer couponRuleId, LocalDateTime time);

    int updateCouponDeleteTime(Integer couponRuleId, LocalDateTime time);

}
