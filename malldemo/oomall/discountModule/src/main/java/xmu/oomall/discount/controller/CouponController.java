package xmu.oomall.discount.controller;

import com.alibaba.druid.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xmu.oomall.discount.domain.coupon.Coupon;
import xmu.oomall.discount.domain.coupon.CouponRule;
import xmu.oomall.discount.service.Impl.CouponServiceImpl;
import xmu.oomall.util.ResponseUtil;


import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
    public List<CouponRule> list()
    {
        List<CouponRule> couponList=couponService.getCouponList();
        return couponList;
    }

    /**
     * 获取用户的优惠券列表
     * @param userId
     * @return
     */
    @GetMapping("/coupons")
    public List<Coupon> mylist(Integer userId)
    {
        List<Coupon> myList=couponService.getCouponMyList(userId);
        return myList;
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


    /**
     * 用户查看当前购物车下单商品订单可用优惠券
     * @param userId
     * @param cartItemIds
     * @return
     */
    @GetMapping("/availableCoupons")
    public Object selectlist(Integer userId,List<Integer> cartItemIds){
        //把购物车中商品可用优惠券提出来
        Integer itemId;
        Integer productId;
        Integer goodsId;
        List<Integer> goodsIdList=new ArrayList<Integer>(cartItemIds.size());
        for(int i=0;i<cartItemIds.size();i++) {
            itemId = cartItemIds.get(i);
            productId = couponService.getProductId(itemId);//通过cartItemId找货品id
            goodsId = couponService.getGoodsId(productId);//通过货品id找商品id
            goodsIdList.add(goodsId);//把购物车中商品id保存到一个list当中

        }
        Set<CouponRule> canUsedCoupons=couponService.getCanUsedCoupons(goodsIdList,userId);
        return canUsedCoupons;
    }

    /**
     * Order模块调用Discount模块，计算使用优惠券后的价格
     * @param cartItemIds
     * @param couponId
     * @return
     */
//    @GetMapping("/calcDiscount")
//    public Object calcDiscount(List<Integer> cartItemIds,String couponSn)
//    {
//
//    }

}
