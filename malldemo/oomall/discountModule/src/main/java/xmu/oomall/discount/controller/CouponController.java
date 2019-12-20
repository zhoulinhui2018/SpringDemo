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
     * @description 管理员下架优惠券规则（测试已通过）
     * @param id 被修改的优惠券规则id
     * @return java.lang.Object[]
     * @author Zhang Yaqing
     * @date 2019/12/20
     */
    @PostMapping("/couponRules/{id}/invalid")
    public Object invalidate(@PathVariable Integer id,
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
        log.setActions("下架优惠券规则");

        CouponRulePo couponRulePo;
        //如果id不存在，抛出异常
        try{
            couponRulePo=couponService.findCouponRuleById(id);
        }catch (Exception e){
            log.setStatusCode(0);
            couponService.log(log);
            return ResponseUtil.badArgument();
        }

        //管理员不能下架不在起止时限内的、或已作废的、或已删除的优惠券规则
        LocalDateTime beginTime=couponRulePo.getBeginTime();
        LocalDateTime endTime=couponRulePo.getEndTime();
        LocalDateTime nowTime=LocalDateTime.now();
        boolean inTime=nowTime.isAfter(beginTime)&&nowTime.isBefore(endTime);
        Boolean statusCode=couponRulePo.getStatusCode();
        Boolean isDeleted=couponRulePo.getBeDeleted();
        if(!inTime||!statusCode||isDeleted){
            log.setStatusCode(0);
            couponService.log(log);
            return ResponseUtil.updateCouponRuleFailed();
        }

        //优惠卷作废后，所有领出未用的优惠券也一并作废，已经使用的优惠卷不受影响
        //先修改优惠券状态
        try{
            couponService.invalidate(id);
        }catch (Exception e){
            log.setStatusCode(0);
            couponService.log(log);
            return ResponseUtil.updateCouponRuleFailed();
        }
        //再对优惠券进行操作
        try{
            couponService.invalidateCoupons(id);
        }catch (Exception e){
            log.setStatusCode(0);
            couponService.log(log);
            return ResponseUtil.serious();
        }
        couponService.log(log);
        return ResponseUtil.ok();
    }


    /**
     * @description 管理员删除优惠券规则（测试已通过）
     * @param id 被删除的优惠券规则id
     * @return java.lang.Object[]
     * @author Zhang Yaqing
     * @date 2019/12/10
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

        CouponRulePo couponRulePo=couponService.findCouponRuleById(id);

        //管理员不能删除在起止时限内的、已删除的优惠券
        LocalDateTime beginTime=couponRulePo.getBeginTime();
        LocalDateTime endTime=couponRulePo.getEndTime();
        LocalDateTime nowTime=LocalDateTime.now();
        boolean inTime=nowTime.isAfter(beginTime)&&nowTime.isBefore(endTime);
        Boolean isDeleted=couponRulePo.getBeDeleted();
        if(inTime||isDeleted){
            log.setStatusCode(0);
            couponService.log(log);
            return ResponseUtil.deleteCouponRuleFailed();
        }
        try{
            couponService.deleteCouponRuleById(id);
        }
        catch (Exception e){
            log.setStatusCode(0);
            couponService.log(log);
            return ResponseUtil.deleteCouponRuleFailed();
        }
        couponService.log(log);
        return ResponseUtil.ok();
    }

    /**
     * @description 用户查看优惠劵规则列表，用户不能看未开启的优惠劵（测试已通过）
     * @param page 第几页
     * @param limit 一页多少
     * @return java.lang.Object[List<couponRulePo>]
     * @author Zhang Yaqing
     * @date 2019/12/10
     */
    @GetMapping("/couponRules")
    public Object userGetCouponRules(@RequestParam(defaultValue = "1") Integer page,
                                     @RequestParam(defaultValue = "10") Integer limit,
                                     HttpServletRequest request){
        String id=request.getHeader("id");
        Integer userId=Integer.valueOf(id);
        if (userId==null){
            return ResponseUtil.unlogin();
        }

        List<CouponRulePo> couponRulePoList;
        try{
            couponRulePoList = couponService.getUserCouponRules(page,limit);
        }
        catch (Exception e){
            return ResponseUtil.serious();
        }
        return ResponseUtil.ok(couponRulePoList);
    }


    /**
     * @description 用户查看不同状态（0未使用，1已使用，2已失效，3已过期）的优惠劵
     * @param showType 不同状态
     * @param page 第几页
     * @param limit 一页多少
     * @return java.lang.Object[List<coupon>]
     * @author Zhang Yaqing
     * @date 2019/12/10
     */
    @GetMapping("/coupons")
    public Object mylist(@RequestParam Integer showType,
                         @RequestParam(defaultValue = "1") Integer page,
                         @RequestParam(defaultValue = "10") Integer limit,
                         HttpServletRequest request) {
        String id=request.getHeader("id");
        if (id==null){
            return ResponseUtil.unlogin();
        }
        Integer userId=Integer.valueOf(id);
        //获取相应的couponList
        //***** 注意这里！！！！*******/////
        //下层进行查找和封装，在dao层分两种情况：
        // 1、当showType=0/1/2时，直接用status搜索
        // 2、当showType=3时，在dao层先获取user的所有couponPo，判断是否已失效，再加入列表返回

        //另外关于封装问题，Coupon=CouponPo+CouponRule，CouponRule=CouponRule+CouponStrategy
        //最后需要的是Coupon，所以需要双层的封装，记得在下层实现好！！
        List<Coupon> couponList;
//        try{
//            couponList=couponService.showTypeCouponList(page,limit,userId,showType);
//        }
//        catch (Exception e){
//            return ResponseUtil.serious();
//        }
//        return ResponseUtil.ok(couponList);
        return null;
    }

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
     * @description 用户领取一张新的优惠券（测试已通过）
     * @param couponPo 优惠券
     * @return java.lang.Object[couponPo]
     * @author Zhang Yaqing
     * @date 2019/12/10
     */
    @PostMapping("/coupons")
    public Object createACoupon(HttpServletRequest request,@RequestBody CouponPo couponPo)
    {
        String id=request.getHeader("id");
        if(id==null){
            return ResponseUtil.unlogin();
        }
        if (!validateCoupon(couponPo)) {
            return ResponseUtil.invalidCoupon();
        }
//        判断是否在可领取时间内、是否已删除等
        LocalDateTime beginTime=couponPo.getBeginTime();
        LocalDateTime endTime=couponPo.getEndTime();
        LocalDateTime nowTime=LocalDateTime.now();
        boolean inTime=nowTime.isAfter(beginTime)&&nowTime.isBefore(endTime);
        Boolean isDeleted=couponPo.getBeDeleted();
        if(!inTime||isDeleted){
            return ResponseUtil.invalidCoupon();
        }

        Integer userId=Integer.valueOf(id);
        Integer couponRuleId=couponPo.getCouponRuleId();

        System.out.println("userId:"+userId);
        System.out.println("couponRuleId:"+couponRuleId);
        System.out.println("couponPo:"+couponPo);


        //判断此couponRuleId的优惠券是否已经领取过，即是否在coupon表中有
        //获取该用户所有的优惠券，遍历列表，如果有领过，返回错误
        List<CouponPo> userAllCouponPos=couponService.getCouponMyList(userId);
        for(CouponPo coupon:userAllCouponPos){
            if(couponRuleId.equals(coupon.getCouponRuleId())){
                return ResponseUtil.getCouponFailed();
            }
        }

        //如果没有领过，调用service层的领取优惠券，新增优惠券po
        try {
            couponService.addCoupon(couponPo);
        }catch (Exception e){
            ResponseUtil.getCouponFailed();
        }
        return ResponseUtil.ok(couponPo);
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

    //热门优惠券的抢购 消息队列吗？
    //管理员把优惠券规则置失效之后
    //已经使用优惠券的就正常使用了，后面的就没有这个优惠券了
    //把团购规则置失效之后，已经支付的团购处理退款
    //把预售规则置失效之后，已经支付定金的退定金，已经付了定金和尾款的全部退款




}
