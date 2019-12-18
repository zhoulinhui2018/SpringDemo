package xmu.oomall.discount.service.Impl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import xmu.oomall.discount.dao.CouponDao;
import xmu.oomall.discount.domain.Log;
import xmu.oomall.discount.domain.OrderItem;
import xmu.oomall.discount.domain.coupon.CouponPo;
import xmu.oomall.discount.domain.coupon.CouponRulePo;
import xmu.oomall.discount.service.ICouponService;

import java.util.List;
import java.util.Set;

/**
 * @Author: Ming Qiu
 * @Description:优惠卷服务的实现
 * @Date: Created in 11:03 2019/11/19
 * @Modified By:
 **/
@Service
public class CouponServiceImpl implements ICouponService {
    @Autowired
    private CouponDao couponDao;

    @Autowired
    private LoadBalancerClient loadBalancerClient;


    @Override
    public List<CouponPo> getMyCoupons0(Integer page, Integer limit, Integer userId) {
        PageHelper.startPage(page,limit);
        return couponDao.getMyCoupons0(userId);
    }

    @Override
    public List<CouponPo> getMyCoupons1(Integer page, Integer limit, Integer userId) {
        PageHelper.startPage(page,limit);
        return couponDao.getMyCoupons1(userId);
    }

    @Override
    public List<CouponPo> getMyCoupons2(Integer page, Integer limit, Integer userId) {
        PageHelper.startPage(page,limit);
        return couponDao.getMyCoupons2(userId);
    }

    @Override
    public List<CouponPo> getMyCoupons3(Integer page, Integer limit, Integer userId) {
        PageHelper.startPage(page,limit);
        return couponDao.getMyCoupons3(userId);
    }

    @Override
    public void log(Log log){
        RestTemplate restTemplate = new RestTemplate();
        ServiceInstance instance = loadBalancerClient.choose("logService");
        System.out.println(instance.getHost());
        System.out.println(instance.getPort());
        String reqURL = String.format("http://%s:%s", instance.getHost(), instance.getPort() + "/logs");
        restTemplate.postForObject(reqURL,log,Log.class);
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
    public List<CouponRulePo> findUserCouponRules(){
        return couponDao.findUserCouponRules();
    }
    @Override
    public void addCouponRule(CouponRulePo couponRule) {
        couponDao.addCouponRule(couponRule);

    }

    @Override
    public void addCoupon(CouponPo coupon) {
        couponDao.addCoupon(coupon);
    }

    @Override
    public List<CouponPo> getCouponMyList(Integer userId,Integer page,Integer limit){
        PageHelper.startPage(page,limit);
        List<CouponPo> mylist=couponDao.getCouponMyList(userId);
        return mylist;
    }
    @Override
    public CouponPo findCouponById(Integer id){
        CouponPo coupon=couponDao.findCouponById(id);
        return coupon;
    }


    @Override
    public Set<CouponRulePo> getCanUsedCoupons(List<Integer> goodsIdList, Integer userId){
        Set<CouponRulePo> canUsedCoupons=couponDao.getCanUsedCoupons(goodsIdList,userId);
        return canUsedCoupons;
    }

    @Override
    public List<OrderItem> calcDiscount(List<OrderItem> orderItems, Integer couponId){
        List<OrderItem> newItems=couponDao.calcDiscount(orderItems,couponId);
        return newItems;
    }

    /**
     * 根据优惠券规则id更改coupon的状态
     * @param id
     */
    @Override
    public int updateCouponStatus(Integer id){
        return couponDao.updateCouponStatus(id);
    }

    /**
     * 优惠券使用后修改状态
     * @param userId
     * @param couponId
     * @return
     */
    @Override
    public int updateUserCouponStatus(Integer userId, Integer couponId){
        return couponDao.updateUserCouponStatus(userId,couponId);
    }

}
