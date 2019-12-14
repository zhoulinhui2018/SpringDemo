package xmu.oomall.discount.controller;

import com.alibaba.druid.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xmu.oomall.discount.controller.vo.OrderVo;
import xmu.oomall.discount.domain.*;
import xmu.oomall.discount.domain.coupon.CouponPo;
import xmu.oomall.discount.domain.coupon.CouponRulePo;
import xmu.oomall.discount.service.Impl.CouponServiceImpl;
import xmu.oomall.util.ResponseUtil;


import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("")
public class CouponController {
    @Autowired
    private CouponServiceImpl couponService;

    @Autowired
    private CartItemController cartItemController;

    private Object validate(CouponRulePo couponRule) {
        String name = couponRule.getName();
        if (StringUtils.isEmpty(name)) {
            return ResponseUtil.badArgument();
        }
        return null;
    }
    private Object validateCoupon(CouponPo coupon) {
        String name = coupon.getName();
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
    public List<CouponRulePo> list()
    {
        List<CouponRulePo> couponList=couponService.getCouponList();
        return couponList;
    }


    /**
     * 管理员新建优惠券
     * @param couponRule
     * @return
     */
    @PostMapping("/couponRules")
    public Object create(@RequestBody CouponRulePo couponRule)
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
        CouponRulePo couponRule=couponService.findCouponRuleById(id);
        return ResponseUtil.ok(couponRule);
    }

    /**
     * 管理员更新优惠券信息
     * @param couponRule
     * @return
     */
    @PutMapping("/couponRules/{id}")
    public Object update(@RequestBody CouponRulePo couponRule) {
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
    public Object delete(@RequestBody CouponRulePo couponRule){
        couponService.deleteCouponRuleById(couponRule.getId());
        return ResponseUtil.ok();
    }

    /**
     * 获取用户的优惠券列表
     * @param userId
     * @return
     */
    @GetMapping("/coupons")
    public List<CouponPo> mylist(Integer userId)
    {
        List<CouponPo> myList=couponService.getCouponMyList(userId);
        return myList;
    }

    /**
     * 根据id查询coupon表
     * @param id
     * @return
     */
    @GetMapping("/coupons/{id}")
    public Object readACoupon(Integer id)
    {
        CouponPo ACoupon=couponService.findCouponById(id);
        return ResponseUtil.ok(ACoupon);
    }

    @PostMapping("/coupons")
    public Object createACoupon(@RequestBody CouponPo coupon)
    {
        Object error=validateCoupon(coupon);
        if (error != null) {
            return error;
        }
        couponService.addCoupon(coupon);
        return ResponseUtil.ok(coupon);
    }

    /**
     * 用户查看当前购物车下单商品订单可用优惠券
     * @param userId
     * @param cartItemIds
     * @return
     */
    @GetMapping("/coupons/availableCoupons")
    //OrderVo可以使用在这里
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
        Set<CouponRulePo> canUsedCoupons=couponService.getCanUsedCoupons(goodsIdList,userId);
        return ResponseUtil.ok(canUsedCoupons);
    }

    /**
     * Order模块调用Discount模块，把List<OrderItem>传过来计算使用优惠券后的价格,返回计算优惠价格后的新明细
     * @param orderVo
     * @return List<OrderItem>
     */
    @GetMapping("/calcDiscount")
    public List<OrderItemPo> calcDiscount(OrderVo orderVo)
    {

        Integer couponId=orderVo.getCouponId();
        //根据couponId查找coupon对象
        //Coupon coupon=couponService.findCouponById(couponId);
        List<Integer> cartItemIds=new ArrayList<Integer>(orderVo.getCartItemIds().size());
        CartItemPo item;
        OrderItemPo orderItem;
        BigDecimal price;

        //List<CartItem> cartItems=new ArrayList<CartItem>(cartItemIds.size());
        List<OrderItemPo> orderItems=new ArrayList<>(cartItemIds.size());
        for(Integer cartId:orderVo.getCartItemIds())
        {
            item= cartItemController.getCartItemById(cartId);
            orderItem=new OrderItemPo(item);
            price=cartItemController.getProductPrice(item.getProductId());
            orderItem.setPrice(price);
            orderItems.add(orderItem);
        }
        List<OrderItemPo> newItems=couponService.calcDiscount(orderItems,couponId);
        return newItems;

    }

}
