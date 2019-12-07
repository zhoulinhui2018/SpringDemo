package xmu.oomall.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;
import xmu.oomall.domain.coupon.CouponRule;
import xmu.oomall.domain.coupon.CouponRulePo;
import xmu.oomall.domain.coupon.Coupon;

import java.util.List;

/**
 * @Author: Ming Qiu
 * @Description:优惠卷Mapper接口
 * @Date: Created in 11:03 2019/11/19
 * @Modified By:
 **/

@Mapper
@Service
public interface CouponMapper {

    /**
     * 用id找优惠卷规则
     * @param id 优惠卷规则id
     * @return 优惠
     */
    CouponRule findCouponRuleById(Integer id);

    /**
     * 管理员增加优惠券
     * @param couponRule
     */
    void addCouponRule(CouponRule couponRule);

    /**
     * 获取优惠劵列表
     * @return List<CouponRule>
     */
    List<CouponRule> getCouponList();

    /**
     * 删除优惠券
     * @param id
     * @return
     */
    Integer deleteCouponRuleById(Integer id);

    /**
     * 更新优惠券信息
     * @param couponRule
     * @return
     */
    Integer updateCouponRuleById(CouponRule couponRule);
}
