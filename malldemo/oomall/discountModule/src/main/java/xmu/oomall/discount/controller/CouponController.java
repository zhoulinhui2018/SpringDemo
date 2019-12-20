package xmu.oomall.discount.controller;

import com.alibaba.druid.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xmu.oomall.discount.domain.CartItem;
import xmu.oomall.discount.domain.Log;
import xmu.oomall.discount.domain.coupon.Coupon;
import xmu.oomall.discount.domain.coupon.CouponPo;
import xmu.oomall.discount.domain.coupon.CouponRulePo;
import xmu.oomall.discount.service.Impl.CouponServiceImpl;
import xmu.oomall.discount.util.ResponseUtil;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("")
public class CouponController {
    @Autowired
    private CouponServiceImpl couponService;

    //判断优惠券规则是否有效
    private Boolean validateCouponRule(CouponRulePo couponRule) {
        String name = couponRule.getName();
        if (name==null) {
            return false;
        }
        return true;
    }
    //判断优惠券是否有效
    private Boolean validateCoupon(CouponPo coupon) {
        String name = coupon.getName();
        if (name==null) {
            return false;
        }
        return true;
    }

    /**
     * @description 管理员获取优惠券列表（测试已通过）
     * @param page 第几页
     * @param limit 最多几页
     * @return java.lang.Object[List<couponRulePo>]
     * @author Zhang Yaqing
     * @date 2019/12/10
     */
    @GetMapping("/admin/couponRules")
    public Object list(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       HttpServletRequest request) {
        String adminid= request.getHeader("id");
        if (adminid==null){
            return ResponseUtil.unlogin();
        }
        Log log=new Log();
        log.setAdminId(Integer.valueOf(adminid));
        log.setIp(request.getRemoteAddr());
        log.setType(0);
        log.setStatusCode(1);
        log.setActions("查看优惠券规则列表");

        List<CouponRulePo> couponRulePoList;
        try{
            couponRulePoList = couponService.getCouponList(page,limit);
        }
        catch (Exception e){
            log.setStatusCode(0);
            couponService.log(log);
            return ResponseUtil.serious();
        }
        couponService.log(log);
        return ResponseUtil.ok(couponRulePoList);
    }



    /**
     * @description 管理员新建优惠券规则（测试已通过，问题是validate函数需要重新写）
     * @param couponRulePo 优惠券规则
     * @return java.lang.Object[couponRulePo]
     * @author Zhang Yaqing
     * @date 2019/12/10
     */
    @PostMapping("/couponRules")
    public Object create(@RequestBody CouponRulePo couponRulePo,HttpServletRequest request)
    {
        String adminid= request.getHeader("id");
        if (adminid==null){
            return ResponseUtil.unlogin();
        }
        Log log=new Log();
        log.setAdminId(Integer.valueOf(adminid));
        log.setIp(request.getRemoteAddr());
        log.setType(1);
        log.setStatusCode(1);
        log.setActions("新建优惠券规则");

        //couponRule内容不合理
        if (!validateCouponRule(couponRulePo)) {
            log.setStatusCode(0);
            couponService.log(log);
            return ResponseUtil.invalidCouponRule();
        }

        try {
            couponService.addCouponRule(couponRulePo);
        }catch (Exception e){
            return ResponseUtil.addCouponRuleFailed();
        }
        couponService.log(log);
        return ResponseUtil.ok(couponRulePo);
    }

    /**
     * @description 管理员查看优惠券规则详情（测试已通过）
     * @param id
     * @return java.lang.Object[couponRulePo]
     * @author Zhang Yaqing
     * @date 2019/12/10
     */
    @GetMapping("/couponRules/{id}")
    public Object detail(@PathVariable Integer id,HttpServletRequest request)
    {
        String adminid= request.getHeader("id");
        if (adminid==null){
            return ResponseUtil.unlogin();
        }
        Log log=new Log();
        log.setAdminId(Integer.valueOf(adminid));
        log.setIp(request.getRemoteAddr());
        log.setType(0);
        log.setStatusCode(1);
        log.setActionId(id);
        log.setActions("查看优惠券规则");
        CouponRulePo couponRulePo;
        try {
            couponRulePo=couponService.findCouponRuleById(id);
        }catch (Exception e){
            log.setStatusCode(0);
            couponService.log(log);
            return ResponseUtil.invalidPresaleRule();
        }

        if(couponRulePo==null){
            log.setStatusCode(0);
            couponService.log(log);
            return ResponseUtil.badArgument();
        }

        couponService.log(log);
        return ResponseUtil.ok(couponRulePo);
    }

    /**
     * @description 管理员修改优惠券规则（测试已通过）
     * @param id 被修改的优惠券规则id
     * @param couponRulePo 新的优惠券规则Po
     * @return java.lang.Object[couponRulePo]
     * @author Zhang Yaqing
     * @date 2019/12/10
     */
    @PutMapping("/couponRules/{id}")
    public Object update(@PathVariable Integer id,
                         @RequestBody CouponRulePo couponRulePo,
                         HttpServletRequest request) {
        String adminid= request.getHeader("id");
        if (adminid==null){
            return ResponseUtil.unlogin();
        }
        Log log=new Log();
        log.setAdminId(Integer.valueOf(adminid));
        log.setIp(request.getRemoteAddr());
        log.setType(2);
        log.setStatusCode(1);
        log.setActionId(id);
        log.setActions("修改优惠券规则");

        couponRulePo.setId(id);
        if (!validateCouponRule(couponRulePo)) {
            log.setStatusCode(0);
            couponService.log(log);
            return ResponseUtil.invalidCouponRule();
        }

        //管理员不能修改在起止时限内的优惠券
        LocalDateTime beginTime=couponRulePo.getBeginTime();
        LocalDateTime endTime=couponRulePo.getEndTime();
        LocalDateTime nowTime=LocalDateTime.now();
        boolean inTime=nowTime.isAfter(beginTime)&&nowTime.isBefore(endTime);
        if(inTime){
            log.setStatusCode(0);
            couponService.log(log);
            return ResponseUtil.updateCouponRuleFailed();
        }

        try{
            couponService.updateCouponRuleById(couponRulePo);
        }
        catch (Exception e){
            log.setStatusCode(0);
            couponService.log(log);
            return ResponseUtil.updateCouponRuleFailed();
        }
        couponService.log(log);
        return ResponseUtil.ok(couponRulePo);
    }



    /**
     * @description 管理员下架优惠券规则
     * @param id 被修改的优惠券规则id
     * @return java.lang.Object[couponRulePo]
     * @author Zhang Yaqing
     * @date 2019/12/10
     */
    @PostMapping("/couponRules/{id}/invalid")
//    public Object invalidate(@PathVariable Integer id,
//                         HttpServletRequest request) {
//        String adminid= request.getHeader("id");
//        if (adminid==null){
//            return ResponseUtil.unlogin();
//        }
//        Log log=new Log();
//        log.setAdminId(Integer.valueOf(adminid));
//        log.setIp(request.getRemoteAddr());
//        log.setType(2);
//        log.setStatusCode(1);
//        log.setActionId(id);
//        log.setActions("下架优惠券规则");
//
//        CouponRulePo couponRulePo;
//        //如果id不存在，抛出异常
//        try{
//            couponRulePo=couponService.findCouponRuleById(id);
//        }catch (Exception e){
//            log.setStatusCode(0);
//            couponService.log(log);
//            return ResponseUtil.badArgument();
//        }
//
//        //管理员不能下架在起止时限内的优惠券规则
//        LocalDateTime beginTime=couponRulePo.getBeginTime();
//        LocalDateTime endTime=couponRulePo.getEndTime();
//        LocalDateTime nowTime=LocalDateTime.now();
//        boolean inTime=nowTime.isAfter(beginTime)&&nowTime.isBefore(endTime);
//        if(inTime){
//            log.setStatusCode(0);
//            couponService.log(log);
//            return ResponseUtil.updateCouponRuleFailed();
//        }
//        //优惠卷作废后，所有领出未用的优惠卷也一并作废，已经使用的优惠卷不受影响
//
//        if (!validateCouponRule(couponRulePo)) {
//            log.setStatusCode(0);
//            couponService.log(log);
//            return ResponseUtil.invalidCouponRule();
//        }
//        couponRulePo.setId(id);
//        CouponRulePo ruleInDB=couponService.findCouponRuleById(id);
//
//        boolean oldStatusCode=ruleInDB.getStatusCode();
//
//
//
//        if (beginTime.isBefore(nowTime) && endTime.isAfter(nowTime)){
//            inTime=true;
//        }
//        if(inTime==true && oldStatusCode==true && couponRule.getStatusCode()==false) {
//            //先修改预售状态
//            couponService.updateCouponRuleById(couponRule);
//            couponService.updateCouponStatus(id);
//            return ResponseUtil.ok(couponRule);
//            //再进行退款操作
//
//
//        }else if(inTime==false){
//            //预售未开始或者已经结束可以修改信息
//            if (couponService.updateCouponRuleById(couponRule) == 0) {
//                log.setStatusCode(0);
//                couponService.log(log);
//                return ResponseUtil.fail(711,"优惠券规则修改失败");
//            }
//            return ResponseUtil.ok(couponRule);
//
//        }else{
//            //在预售开始到结束时间内且未作废的情况，不能改动信息
//            log.setStatusCode(0);
//            couponService.log(log);
//            return ResponseUtil.fail(711,"优惠券规则修改失败");
//        }
//
//    }

    /**
     * 管理员删除优惠券规则（已修改）
     * 修改内容如下：1.添加Http头部 2.添加log（好多种情况，得仔细检查）
     * @param id
     * @return
     */
    @DeleteMapping("/couponRules/{id}")
    public Object delete(@PathVariable Integer id,HttpServletRequest request){
        String adminid= request.getHeader("id");
        if (adminid==null){
            return ResponseUtil.unlogin();
        }
        Log log=new Log();
        log.setAdminId(Integer.valueOf(adminid));
        log.setIp(request.getRemoteAddr());
        log.setType(3);
        log.setStatusCode(1);
        log.setActionId(id);
        log.setActions("删除优惠券规则");

        CouponRulePo ruleInDB=couponService.findCouponRuleById(id);
        System.out.println(ruleInDB);
        Boolean statusCode=ruleInDB.getStatusCode();
        LocalDateTime beginTime=ruleInDB.getBeginTime();
        LocalDateTime endTime=ruleInDB.getEndTime();
        LocalDateTime nowTime=LocalDateTime.now();

        Boolean inTime=(nowTime.compareTo(beginTime) >= 0) && (nowTime.compareTo(endTime) <= 0);

        //优惠规则已失效，或不在时间范围内，可以删除
        if(statusCode==false||!inTime){
            int result=couponService.deleteCouponRuleById(id);
            if(result==0){ //出现非法id情况，删除失败
                log.setStatusCode(0);
                couponService.log(log);
                return ResponseUtil.fail(713,"优惠券规则删除失败");
            }else{ //成功删除
                log.setStatusCode(1);
                couponService.log(log);
                return ResponseUtil.ok();
            }
        }
        else{ //不能删除
            log.setStatusCode(0);
            couponService.log(log);
            return ResponseUtil.fail(713,"优惠券规则删除失败");
        }
    }

    /**
     * 用户获取自己领取的优惠券列表（失效的statusCode=0不显示）（不需要添加log）
     * @param request
     * @param page
     * @param limit
     * @return
     */


//    @GetMapping("/coupons")
//    public Object mylist(HttpServletRequest request,
//                                 @RequestParam(defaultValue = "1") Integer page,
//                                 @RequestParam(defaultValue = "10") Integer limit,
//                                 @RequestParam Integer showType)
//    {
//        String id=request.getHeader("id");
//        Integer userId=Integer.valueOf(id);
//        if (userId==null){
//            return ResponseUtil.unlogin();
//        }

//        if (showType==0){
//            List<CouponPo> myCoupons0 = couponService.getMyCoupons0(page, limit, userId);
//            if (myCoupons0==null){
//                return ResponseUtil.ok();
//            }return ResponseUtil.ok(myCoupons0);
//        }else if (showType==1){
//            List<CouponPo> myCoupons1 = couponService.getMyCoupons1(page, limit, userId);
//            if (myCoupons1==null){
//                return ResponseUtil.ok();
//            }return ResponseUtil.ok(myCoupons1);
//        }else if (showType==2){
//            List<CouponPo> myCoupons2 = couponService.getMyCoupons2(page, limit, userId);
//            if (myCoupons2==null){
//                return ResponseUtil.ok();
//            }return ResponseUtil.ok(myCoupons2);
//        }else{
//            List<CouponPo> myCoupons3 = couponService.getMyCoupons3(page, limit, userId);
//            if (myCoupons3==null){
//                return ResponseUtil.ok();
//            }return ResponseUtil.ok(myCoupons3);
//        }
//    }

    /**
     * 根据id查询coupon表（已修改）
     * 修改内容如下：1.对于非法id的判断
     * @param id
     * @return
     * 备注：标准组没有该url
     */
    @GetMapping("/coupons/{id}")
    public Object readACoupon(Integer id)
    {
        CouponPo ACoupon=new CouponPo();
        try {
             ACoupon = couponService.findCouponById(id);
        }catch (Exception e){
           return ResponseUtil.fail();
        }
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
        List<CouponPo> couponPos=couponService.getCouponMyList(userId,1,100);
        List<Integer> couponRuleIds=new ArrayList<>();
        //获取已领取优惠券的ids
        for(CouponPo couponPo:couponPos){
            couponRuleIds.add(couponPo.getCouponRuleId());
        }
        for(Integer eachId:couponRuleIds){
            if(couponRuleId.equals(eachId)){
                //优惠券已经领取过了
                return ResponseUtil.fail(714,"领取优惠券失败");
            }
        }
        LocalDateTime now=LocalDateTime.now();
        LocalDateTime beginTime=coupon.getBeginTime();
        LocalDateTime endTime=coupon.getEndTime();
        //判断是否在起止期限内
        if((now.compareTo(beginTime)>=0)&&(now.compareTo(endTime)<=0)){
            coupon.setStatusCode(0);
            try {
                couponService.addCoupon(coupon);
            }catch (Exception e){
                ResponseUtil.fail(714,"领取优惠券失败");
            }
            return ResponseUtil.ok(coupon);
        }
        else{
            //优惠券过期了，无法领取
            return ResponseUtil.fail(714,"领取优惠券失败");
        }

    }

    /**
     * 用户查看当前购物车下单商品订单可用优惠券
     * userId不能直接使用！！！
     * @param request
     * @param cartItems
     * @return
     */
    @GetMapping("/coupons/availableCoupons")
    public Object selectAvailableCoupons(HttpServletRequest request,List<CartItem> cartItems){
        String Id=request.getHeader("id");
        Integer userId=Integer.valueOf(Id);

        List<Integer> goodsIdList=new ArrayList<>();
        Integer goodsId;
        for(CartItem item:cartItems){
            goodsId=item.getProduct().getGoodsId();
            goodsIdList.add(goodsId);
        }
        List<Coupon> canUsedCoupons= couponService.getCanUsedCoupons(goodsIdList, userId);

        return ResponseUtil.ok(canUsedCoupons);
    }

    /**
     * 用户查看优惠券列表
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("/couponRules")
    public Object userlist(@RequestParam(defaultValue = "1") Integer page,
                           @RequestParam(defaultValue = "10") Integer limit){
        List<CouponRulePo> userList=couponService.getUserCouponRules(page,limit);
        return ResponseUtil.ok(userList);
    }

    //热门优惠券的抢购 消息队列吗？
    //管理员把优惠券规则置失效之后
    //已经使用优惠券的就正常使用了，后面的就没有这个优惠券了
    //把团购规则置失效之后，已经支付的团购处理退款
    //把预售规则置失效之后，已经支付定金的退定金，已经付了定金和尾款的全部退款




}
