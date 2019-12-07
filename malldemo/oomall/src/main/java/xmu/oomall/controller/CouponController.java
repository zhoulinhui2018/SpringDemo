package xmu.oomall.controller;

import com.alibaba.druid.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xmu.oomall.domain.coupon.Coupon;
import xmu.oomall.domain.coupon.CouponRule;
import xmu.oomall.service.impl.CouponServiceImpl;
import xmu.oomall.util.ResponseUtil;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("")
public class CouponController {
    @Autowired
    private CouponServiceImpl couponService;

    private Object validate(CouponRule couponRule) {
        String name = couponRule.getName();
        if (StringUtils.isEmpty(name)) {
            return ResponseUtil.badArgument();
        }
        return null;
    }
    /**
     * 获取优惠券列表
     * @return list<Coupon>
     */
    @GetMapping("/couponRules")
    public Object list()
    {
        List<CouponRule> couponList=couponService.getCouponList();
        return couponList;
    }

    /**
     * 管理员新建优惠券
     * @param couponRule
     * @return
     */
    @PostMapping("/couponRules")
    public Object create(@RequestBody CouponRule couponRule)
    {
        Object error=validate(couponRule);
        if (error != null) {
            return error;
        }
        couponService.addCouponRule(couponRule);
        return ResponseUtil.ok(couponRule);

    }

    /**
     * 获取某一种优惠券信息
     * @param id
     * @return
     */
    @GetMapping("/couponRules/{id}")
    public Object read(@NotNull Integer id)
    {
        CouponRule couponRule=couponService.findCouponRuleById(id);
        return ResponseUtil.ok(couponRule);
    }

    /**
     * 管理员更新优惠券信息
     * @param couponRule
     * @return
     */
    @PutMapping("/couponRules/{id}")
    public Object update(@RequestBody CouponRule couponRule) {
        Object error = validate(couponRule);
        if (error != null) {
            return error;
        }
        if (couponService.updateCouponRuleById(couponRule) == 0) {
            return ResponseUtil.updatedDataFailed();
        }
        return ResponseUtil.ok(couponRule);
    }

    /**
     * 管理员删除优惠券
     * @param couponRule
     * @return
     */
    @DeleteMapping("/couponRules/{id}")
    public Object delete(@RequestBody CouponRule couponRule){
        couponService.deleteCouponRuleById(couponRule.getId());
        return ResponseUtil.ok();
    }
}
