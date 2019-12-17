package xmu.oomall.discount.controller;

import com.alibaba.druid.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import xmu.oomall.discount.domain.CartItem;
import xmu.oomall.discount.domain.coupon.CouponPo;
import xmu.oomall.discount.domain.coupon.CouponRulePo;
import xmu.oomall.discount.service.Impl.CouponServiceImpl;
import xmu.oomall.util.ResponseUtil;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("")
public class CouponController {
    @Autowired
    private CouponServiceImpl couponService;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

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
    public List<CouponRulePo> list(@RequestParam(defaultValue = "1") Integer page,
                                   @RequestParam(defaultValue = "10") Integer limit)
    {
        List<CouponRulePo> couponList=couponService.getCouponList(page,limit);
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
    public Object detail(@NotNull Integer id)
    {
        CouponRulePo couponRule=couponService.findCouponRuleById(id);
        return ResponseUtil.ok(couponRule);
    }

    /**
     * 管理员修改优惠券信息
     * 管理员可以作废优惠卷，优惠卷作废后，所有领出未用的优惠卷也一并作废，已经使用的优惠卷不受影响
     * @param id
     * @param couponRule
     * @return
     */

    @PutMapping("/couponRules/{id}")
    public Object update(@PathVariable Integer id,@RequestBody CouponRulePo couponRule) {
        Object error = validate(couponRule);
        if (error != null) {
            return error;
        }
        couponRule.setId(id);
        CouponRulePo ruleInDB=couponService.findCouponRuleById(id);

        boolean oldStatusCode=ruleInDB.getStatusCode();

        LocalDateTime beginTime=ruleInDB.getBeginTime();
        LocalDateTime endTime=ruleInDB.getEndTime();
        Boolean inTime=false;
        LocalDateTime nowTime=LocalDateTime.now();
        if (beginTime.isBefore(nowTime) && endTime.isAfter(nowTime)){
            inTime=true;
        }
        if(inTime==true && oldStatusCode==true) {
            //先修改预售状态
            couponService.updateCouponRuleById(couponRule);
            couponService.updateCouponStatus(id);
            return ResponseUtil.ok();
            //再进行退款操作


        }else if(inTime==false){
            //预售未开始或者已经结束可以修改信息
            if (couponService.updateCouponRuleById(couponRule) == 0) {
                return ResponseUtil.updatedDataFailed();
            }
            return ResponseUtil.ok(couponRule);

        }else{
            //在预售开始到结束时间内且未作废的情况，不能改动信息
            return ResponseUtil.updatedDataFailed();
        }

    }

    /**
     * 管理员删除优惠券规则
     * @param id
     * @return
     */
    @DeleteMapping("/couponRules/{id}")
    public Object delete(@PathVariable Integer id){

        CouponRulePo ruleInDB=couponService.findCouponRuleById(id);
        Boolean statusCode=ruleInDB.getStatusCode();
        LocalDateTime beginTime=ruleInDB.getBeginTime();
        LocalDateTime endTime=ruleInDB.getEndTime();
        LocalDateTime nowTime=LocalDateTime.now();
        //优惠券规则未失效
        if(statusCode==true) {
            //在优惠券开始到结束时间内不能删除优惠券
            if ((nowTime.compareTo(beginTime) >= 0) && (nowTime.compareTo(endTime) <= 0)) {
                return ResponseUtil.fail();
            }
            couponService.deleteCouponRuleById(id);
            return ResponseUtil.ok();
        }else {//优惠券规则已失效
            couponService.deleteCouponRuleById(id);
            return ResponseUtil.ok();
        }
    }

    /**
     * 用户获取自己领取的优惠券列表（失效的statusCode=0不显示）
     * @param request
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("/coupons")
    public List<CouponPo> mylist(HttpServletRequest request,
                                 @RequestParam(defaultValue = "1") Integer page,
                                 @RequestParam(defaultValue = "10") Integer limit)
    {
        String id=request.getHeader("id");
        Integer userId=Integer.valueOf(id);

        List<CouponPo> myList=couponService.getCouponMyList(userId,page,limit);
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

    /**
     * 用户领取一张新的优惠券
     * 在起止期限中才可以领用或使用优惠卷
     * 限制每个用户同类优惠卷只能有一张
     * @param coupon
     * @return
     */
    @PostMapping("/coupons")
    public Object createACoupon(HttpServletRequest request,@RequestBody CouponPo coupon)
    {
        Object error=validateCoupon(coupon);
        if (error != null) {
            return error;
        }
        String id=request.getHeader("id");
        Integer userId=Integer.valueOf(id);
        Integer couponRuleId=coupon.getCouponRuleId();
        //判断当前优惠券是否已经领取过
        List<CouponPo> couponPos=couponService.getCouponMyList(userId,1,10);
        List<Integer> couponRuleIds=new ArrayList<>();
        //获取已领取优惠券的ids
        for(CouponPo couponPo:couponPos){
            couponRuleIds.add(couponPo.getCouponRuleId());
        }
        for(Integer eachId:couponRuleIds){
            if(couponRuleId.equals(eachId)){
                //优惠券已经领取过了
                return ResponseUtil.fail();
            }
        }
        LocalDateTime now=LocalDateTime.now();
        LocalDateTime beginTime=coupon.getBeginTime();
        LocalDateTime endTime=coupon.getEndTime();
        //判断是否在起止期限内
        if((now.compareTo(beginTime)>=0)&&(now.compareTo(endTime)<=0)){
            couponService.addCoupon(coupon);
            return ResponseUtil.ok(coupon);
        }
        else{
            //优惠券过期了，无法领取
            return ResponseUtil.fail();
        }

    }

    /**
     * 用户查看当前购物车下单商品订单可用优惠券
     * userId不能直接使用！！！
     * @param request
     * @param cartItemIds
     * @return
     */
    @GetMapping("/coupons/availableCoupons")
    public Object selectAvailableCoupons(HttpServletRequest request,List<Integer> cartItemIds){
        String Id=request.getHeader("id");
        Integer userId=Integer.valueOf(Id);

        Integer goodsId;
        List<Integer> goodsIdList=new ArrayList<Integer>(cartItemIds.size());
        for(int i=0;i<cartItemIds.size();i++) {

            //ribbon
            Integer id = cartItemIds.get(i);
            //调用购物车模块服务通过cartItemId找货品id
            RestTemplate restTemplate = new RestTemplate();
            ServiceInstance instance = loadBalancerClient.choose("Cart");
            String reqURL = String.format("http://%s:%s", instance.getHost(), instance.getPort() + "/cartItems/{id}");
            CartItem cartItem = restTemplate.getForObject(reqURL,CartItem.class,id);

            //通过货品id找商品id
            goodsId=cartItem.getProduct().getGoodsId();

            //把购物车中商品id保存到一个list当中
            goodsIdList.add(goodsId);

        }
        Set<CouponRulePo> canUsedCoupons=couponService.getCanUsedCoupons(goodsIdList,userId);
        return ResponseUtil.ok(canUsedCoupons);
    }


    //热门优惠券的抢购 消息队列吗？
    //管理员把优惠券规则置失效之后
    //已经使用优惠券的就正常使用了，后面的就没有这个优惠券了
    //把团购规则置失效之后，已经支付的团购处理退款
    //把预售规则置失效之后，已经支付定金的退定金，已经付了定金和尾款的全部退款




}
