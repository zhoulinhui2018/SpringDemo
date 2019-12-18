package xmu.oomall.discount.mapper;

import org.apache.ibatis.annotations.Mapper;
import xmu.oomall.discount.domain.coupon.CouponPo;
import xmu.oomall.discount.domain.coupon.CouponRulePo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: XuHuangchao
 * @Description:优惠卷Mapper接口
 * @Date: Created in 11:03 2019/12/8
 * @Modified By:
 **/

@Mapper
public interface CouponMapper {

    public List<CouponPo> getMyCoupons0(Integer userId);

    public List<CouponPo> getMyCoupons1(Integer userId);
    public List<CouponPo> getMyCoupons2(Integer userId);


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
}
