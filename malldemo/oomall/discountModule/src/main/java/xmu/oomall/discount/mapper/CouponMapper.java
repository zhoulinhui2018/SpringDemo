package xmu.oomall.discount.mapper;

import org.apache.ibatis.annotations.Mapper;
import xmu.oomall.discount.domain.CartItem;
import xmu.oomall.discount.domain.coupon.Coupon;
import xmu.oomall.discount.domain.coupon.CouponRule;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

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
    int deleteCouponRuleById(Integer id);

    /**
     * 更新优惠券信息
     * @param couponRule
     * @return
     */
    int updateCouponRuleById(CouponRule couponRule);

    List<Coupon> getCouponMyList(Integer userId);

    Integer getProductId(Integer itemId);

    Integer getGoodsId(Integer productId);

    CouponRule getCouponRule(Integer couponRuleId);

    Coupon findCouponById(Integer id);

    BigDecimal getProductPrice(Integer productId);
}
