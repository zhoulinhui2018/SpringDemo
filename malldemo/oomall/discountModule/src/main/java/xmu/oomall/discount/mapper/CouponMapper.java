package xmu.oomall.discount.mapper;

import org.apache.ibatis.annotations.Mapper;
import xmu.oomall.discount.domain.coupon.CouponPo;
import xmu.oomall.discount.domain.coupon.CouponRulePo;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: XuHuangchao
 * @Description:优惠卷Mapper接口
 * @Date: Created in 11:03 2019/12/8
 * @Modified By:
 **/

@Mapper
public interface CouponMapper {

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
     * 获取优惠劵列表
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

    Integer getProductId(Integer itemId);

    Integer getGoodsId(Integer productId);

    CouponRulePo getCouponRule(Integer couponRuleId);

    CouponPo findCouponById(Integer id);

    BigDecimal getProductPrice(Integer productId);

    void addCoupon(CouponPo coupon);
}
