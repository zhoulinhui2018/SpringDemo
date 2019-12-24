package xmu.oomall.discount.service.impl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import xmu.oomall.discount.dao.CouponDao;
import xmu.oomall.discount.domain.Log;
import xmu.oomall.discount.domain.OrderItem;
import xmu.oomall.discount.domain.coupon.Coupon;
import xmu.oomall.discount.domain.coupon.CouponPo;
import xmu.oomall.discount.domain.coupon.CouponRulePo;
import xmu.oomall.discount.service.ICouponServiceImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Ming Qiu
 * @Description:优惠卷服务的实现
 * @Date: Created in 11:03 2019/11/19
 * @Modified By:
 **/
@Service
public class CouponServiceImpl implements ICouponServiceImpl {
    @Autowired
    private CouponDao couponDao;

    @Autowired
    private LoadBalancerClient loadBalancerClient;



    @Override
    public List<Coupon> showTypeCouponList(Integer page, Integer limit, Integer userId, Integer showType) {
        PageHelper.startPage(page,limit);

        List<CouponPo> couponPoList = couponDao.showTypeCouponList(userId,showType);
        List<Coupon> couponList=new ArrayList<>();
        for (CouponPo couponPo : couponPoList) {
            Coupon coupon = couponDao.convertToCoupon(couponPo);
            couponList.add(coupon);
        }
        return couponList;
    }


    @Override
    public void log(Log log){
        RestTemplate restTemplate = new RestTemplate();
        ServiceInstance instance = loadBalancerClient.choose("logService");
        System.out.println(instance.getHost());
        System.out.println(instance.getPort());
        String reqUrl = String.format("http://%s:%s", instance.getHost(), instance.getPort() + "/logs");
        restTemplate.postForObject(reqUrl,log,Log.class);
    }


    @Override
    public CouponRulePo findCouponRuleById(Integer id) {
        CouponRulePo couponRule=couponDao.findCouponRuleById(id);
        return couponRule;
    }

    @Override
    public int updateCouponRuleById(CouponRulePo couponRule) {
        return couponDao.updateCouponRuleById(couponRule);
    }

    @Override
    public int deleteCouponRuleById(Integer id) {
        return couponDao.deleteCouponRuleById(id);
    }

    @Override
    public List<CouponRulePo> getCouponList(Integer page,Integer limit) {
        PageHelper.startPage(page,limit);
        List<CouponRulePo> couponRuleList=couponDao.getCouponList();
        return couponRuleList;
    }

    @Override
    public void addCouponRule(CouponRulePo couponRule) {
        couponDao.addCouponRule(couponRule);

    }

    @Override
    public int addCoupon(CouponPo coupon) {
        return couponDao.addCoupon(coupon);
    }

    @Override
    public List<CouponPo> getCouponMyList(Integer userId){
        List<CouponPo> mylist=couponDao.getCouponMyList(userId);
        return mylist;
    }
    @Override
    public CouponPo findCouponById(Integer id){
        CouponPo coupon=couponDao.findCouponById(id);
        return coupon;
    }


    @Override
    public List<Coupon> getCanUsedCoupons(List<Integer> goodsIdList, Integer userId){
        List<Coupon> canUsedCoupons=couponDao.getCanUsedCoupons(goodsIdList,userId);
        return canUsedCoupons;
    }

    @Override
    public List<OrderItem> calcDiscount(List<OrderItem> orderItems, Integer couponId){
        System.out.println("aa");
        List<OrderItem> newItems=couponDao.calcDiscount(orderItems,couponId);
        return newItems;
    }

    /**
     * 根据优惠券规则id更改coupon的状态
     * @param id 1
     * @return int
     */
    @Override
    public int updateCouponStatus(Integer id){
        return couponDao.updateCouponStatus(id);
    }

    /**
     * 优惠券使用后修改状态
     * @param userId 1
     * @param couponId 1
     * @return 1
     */
    @Override
    public int updateUserCouponStatus(Integer userId, Integer couponId){
        return couponDao.updateUserCouponStatus(userId,couponId);
    }

    /**
     * 用户查看优惠券规则
     * @param page 1
     * @param limit 1
     * @return 1
     */
    @Override
    public List<CouponRulePo> getUserCouponRules(Integer page, Integer limit){
        PageHelper.startPage(page,limit);
        List<CouponRulePo> userList=couponDao.getUserCouponRules();
        return userList;

    }

    @Override
    public int modifiedCouponRuleNum(Integer couponRuleId){
        return couponDao.modifiedCouponRuleNum(couponRuleId);
    }

    public int invalidate(Integer couponRuleId) {
        return couponDao.invalidate(couponRuleId);
    }

    public int invalidateCoupons(Integer couponRuleId) {
        List<CouponPo> couponPoList=couponDao.getCouponsByRuleId(couponRuleId);
        //所有领出未用的优惠券也一并作废，已经使用的优惠卷不受影响
        for(int i=0;i<couponPoList.size();i++){
            CouponPo couponPo=couponPoList.get(i);
            System.out.println("第"+i+"个："+couponPo);
            if(couponPo.getStatusCode()==0){
                couponDao.invalidateCouponById(couponPo.getId());
            }
        }
        return 1;
    }

}
