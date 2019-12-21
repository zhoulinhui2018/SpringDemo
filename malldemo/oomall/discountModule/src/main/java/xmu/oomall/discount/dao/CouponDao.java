package xmu.oomall.discount.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import xmu.oomall.discount.domain.Goods;
import xmu.oomall.discount.domain.OrderItem;
import xmu.oomall.discount.domain.coupon.*;
import xmu.oomall.discount.mapper.CouponMapper;
import xmu.oomall.discount.util.JacksonUtil;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;


/**
 * @Author: Xu Huangchao
 * @Description: 优惠卷的DAO
 * @Date: Created in 17:02 2019/12/7
 * @Modified By:
 **/

@Repository
public class CouponDao {
    private static final Logger logger = LoggerFactory.getLogger(CouponDao.class);
    @Autowired
    private CouponMapper couponMapper;

    ///这里这个方法没有写完！！！！超超看这里！！！
//    public List<CouponPo> showTypeCouponList(Integer userId,Integer showType){
//
//        if(showType==3){
//            LocalDateTime now=LocalDateTime.now();
//
//        }else{
//            return couponMapper.showTypeCouponList(userId,showType);
//        }
//    }


    /**
     * 管理员增加优惠券规则
     * @param couponRule
     */
    public void addCouponRule(CouponRulePo couponRule) {
        couponRule.setGmtCreate(LocalDateTime.now());
        couponRule.setGmtModified(LocalDateTime.now());
        couponRule.setBeDeleted(false);
        couponRule.setStatusCode(true);
        couponMapper.addCouponRule(couponRule);
    }


    /**
     * 管理员获取CouponRule列表
     * @return
     */
    public List<CouponRulePo> getCouponList() {
        List<CouponRulePo> couponRuleList=couponMapper.getCouponList();
        return couponRuleList;
    }

    /**
     * 管理员根据id删除优惠券规则
     * @param id
     * @return
     */
    public int deleteCouponRuleById(Integer id) {
        //需要级联删除所有使用此规则的优惠券
       couponMapper.deleteAllCoupons(id);
       updateCouponDeleteTime(id,LocalDateTime.now());
       updateCouponRuleDeleteTime(id,LocalDateTime.now());
       return couponMapper.deleteCouponRuleById(id);

    }

    public int updateCouponRuleDeleteTime(Integer couponRuleId,LocalDateTime time){
        return couponMapper.updateCouponRuleDeleteTime(couponRuleId,time);
    }

    public int updateCouponDeleteTime(Integer couponRuleId,LocalDateTime time){
        return couponMapper.updateCouponDeleteTime(couponRuleId,time);
    }
    /**
     * 管理员更新优惠券规则
     * @param couponRule
     * @return
     */
    public int updateCouponRuleById(CouponRulePo couponRule) {
        couponRule.setGmtModified(LocalDateTime.now());
        return couponMapper.updateCouponRuleById(couponRule);
    }

    /**
     * 根据id获取优惠券规则
     * @param id
     * @return
     */
    public CouponRulePo findCouponRuleById(Integer id) {
        CouponRulePo couponRule=couponMapper.findCouponRuleById(id);
        return couponRule;
    }

    /**
     * 用户获取自己的优惠券列表
     * @param userId
     * @return
     */
    public List<CouponPo> getCouponMyList(Integer userId) {
        List<CouponPo> myList=couponMapper.getCouponMyList(userId);
        return myList;
    }


    /**
     * 通过用户的优惠券获取相应的优惠券规则
     * @param coupons
     * @return
     */
    public List<CouponRule> getCouponRules(List<CouponPo> coupons)
    {
        List<CouponRule> rules=new ArrayList<>();
        for(int i=0;i<coupons.size();i++)
        {
            CouponRule couponRule=couponMapper.getCouponRule(coupons.get(i).getCouponRuleId());
            rules.add(couponRule);
        }
        return rules;
    }

    /**
     * 根据id找到一张优惠券
     * @param id
     * @return
     */
    public CouponPo findCouponById(Integer id) {
        CouponPo coupon=couponMapper.findCouponById(id);
        return coupon;
    }

    /**
     * 用户领取一张优惠券
     * @param coupon
     * @return
     */
    public int addCoupon(CouponPo coupon) {
        return couponMapper.addCoupon(coupon);
    }


    /**
     * 把couponRulePo转成couponRule
     * @param couponRulePo
     * @return
     */
    public CouponRule convertToCouponRule(CouponRulePo couponRulePo){
        CouponRule couponRule=new CouponRule();
        couponRule.setId(couponRulePo.getId());
        couponRule.setName(couponRulePo.getName());
        couponRule.setBrief(couponRulePo.getBrief());
        couponRule.setBeginTime(couponRulePo.getBeginTime());
        couponRule.setEndTime(couponRulePo.getEndTime());
        couponRule.setStatusCode(couponRulePo.getStatusCode());
        couponRule.setPicUrl(couponRulePo.getPicUrl());
        couponRule.setValidPeriod(couponRulePo.getValidPeriod());
        couponRule.setStrategy(couponRulePo.getStrategy());
        couponRule.setTotal(couponRulePo.getTotal());
        couponRule.setCollectedNum(couponRulePo.getCollectedNum());
        couponRule.setGoodsList1(couponRulePo.getGoodsList1());
        couponRule.setGoodsList2(couponRulePo.getGoodsList2());
        couponRule.setGmtCreate(couponRulePo.getGmtCreate());
        couponRule.setGmtModified(couponRulePo.getGmtModified());
        couponRule.setBeDeleted(couponRulePo.getBeDeleted());
        couponRule.setCouponStrategy(couponRulePo.getStrategy());
        return couponRule;
    }

    /**
     * 把CouponPo转成Coupon
     * @param couponPo
     * @return
     */
    public Coupon convertToCoupon(CouponPo couponPo){
        Coupon coupon=new Coupon();
        coupon.setId(couponPo.getId());
        coupon.setUserId(couponPo.getUserId());
        coupon.setCouponRuleId(couponPo.getCouponRuleId());
        coupon.setCouponSn(couponPo.getCouponSn());
        coupon.setBeginTime(couponPo.getBeginTime());
        coupon.setEndTime(couponPo.getEndTime());
        coupon.setUsedTime(couponPo.getUsedTime());
        coupon.setName(couponPo.getName());
        coupon.setPicUrl(couponPo.getPicUrl());
        coupon.setGmtCreate(couponPo.getGmtCreate());
        coupon.setGmtModified(couponPo.getGmtModified());
        coupon.setBeDeleted(couponPo.getBeDeleted());
        coupon.setStatusCode(couponPo.getStatusCode());
        Integer couponRuleId=couponPo.getCouponRuleId();
        CouponRulePo couponRulePo=couponMapper.findCouponRuleById(couponRuleId);
        CouponRule couponRule=convertToCouponRule(couponRulePo);
        coupon.setCouponRule(couponRule);
        return coupon;
    }
    /**
     * 获取可用优惠券列表
     * @param goodsIdList
     * @param userId
     * @return
     */
    public List<Coupon> getCanUsedCoupons(List<Integer> goodsIdList,Integer userId) {
        //获取用户所有可用优惠券
        List<CouponPo> coupons=couponMapper.getCouponMyList(userId);

        List<Coupon> canUsedCoupons=new ArrayList<>();
        for(int i=0;i<goodsIdList.size();i++)
        {
            for(int j=0;j<coupons.size();j++) {
                //用CouponRule给CouponRulePo赋值
                Integer couponRuleId=coupons.get(j).getCouponRuleId();
                CouponRulePo couponRulePo=couponMapper.findCouponRuleById(couponRuleId);
                Integer goodsId=goodsIdList.get(i);
                if(isUsedOnGoods(goodsId,couponRulePo)==true)
                {
                    Coupon coupon=convertToCoupon(coupons.get(j));
                    CouponRule couponRule=convertToCouponRule(couponRulePo);
                    coupon.setCouponRule(couponRule);
                    canUsedCoupons.add(coupon);

                }
            }
        }
        return canUsedCoupons;
    }



    /**
     * 判断商品是否可用于该优惠券
     * @param goodsId
     * @param couponRule
     * @return
     */
    private boolean isUsedOnGoods(Integer goodsId, CouponRulePo couponRule) {
        System.out.println("usedonGoods");
        List<Integer> goodsIds=new ArrayList<>();
        goodsIds.clear();
        goodsIds.addAll(getGoodsIdsInCouponRule(couponRule));

        System.out.println("addAll");
        if(goodsIds.contains(Goods.ALL_GOODS.getId()))
        {
            return true;
        }

        else{
            return goodsIds.contains(goodsId);
        }
    }

    /**
     * 获得适用商品id列表
     ** { gIDs：[xxx,xxx,xxx,xxx,xxx]}
     * @param couponRule
     * @return
     */
    public List<Integer> getGoodsIdsInCouponRule(CouponRulePo couponRule){
        String jsonString1 = couponRule.getGoodsList1();
        String jsonString2=couponRule.getGoodsList2();
        jsonString1 = org.apache.commons.text.StringEscapeUtils.unescapeJson(jsonString1);
        jsonString2 = org.apache.commons.text.StringEscapeUtils.unescapeJson(jsonString2);
        System.out.println("jsonString1="+jsonString1);
        System.out.println("jsonString2="+jsonString2);

        List<Integer> goodsId1=new ArrayList<>();
        List<Integer> goodsId2=new ArrayList<>();
        if(jsonString1!=null) {
            goodsId1 = JacksonUtil.parseIntegerList(jsonString1, "goodsIds");
            System.out.println("goodsId1参数" + goodsId1);
        }
        if(jsonString2!=null){
            goodsId2=JacksonUtil.parseIntegerList(jsonString2, "goodsIds");
            System.out.println("goodsId2参数"+goodsId2);
        }

        if(goodsId2!=null) {
            for (Integer goodsId : goodsId2) {
                goodsId1.add(goodsId);
            }
        }
        System.out.println(goodsId1);
        return goodsId1;


    }


    /**
     * 获得能用于此优惠卷的明细
     * @param items 订单明细
     * @return 适用的订单明细
     */
    private List<OrderItem> getValidItems(List<OrderItem> items, Integer couponId){
        System.out.println("getValidItems参数：items = "+items);
        List<OrderItem> validItems = new ArrayList<OrderItem>(items.size());
        CouponPo coupon=couponMapper.findCouponById(couponId);
        System.out.println("coupon");
        Integer couponRuleId=coupon.getCouponRuleId();
        CouponRulePo couponRule=couponMapper.findCouponRuleById(couponRuleId);
        System.out.println("couponRule");

        for (OrderItem item: items){
            Integer goodsId=item.getProduct().getGoodsId();
            if (isUsedOnGoods(goodsId,couponRule)){
                validItems.add(item);
            }
        }
        return validItems;

    }

    /**
     * 获得折扣策略
     * JSON格式:{ name：“XXX”, obj:{XXX}}
     * @return 折扣策略对象
     */
    public AbstractCouponStrategy getStrategy(CouponRulePo couponRule) {
        System.out.println("getStrategy参数：");
        String jsonString = couponRule.getStrategy();
        System.out.println("jsonString = "+ jsonString);
        jsonString = org.apache.commons.text.StringEscapeUtils.unescapeJson(jsonString);
        String strategyName = JacksonUtil.parseString(jsonString, "name");

        AbstractCouponStrategy strategy = null;
        try {
            strategy = (AbstractCouponStrategy) JacksonUtil.parseObject(jsonString, "obj", Class.forName(strategyName));
        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage(), e);
        }
        System.out.println(strategy);
        return strategy;
    }

    /**
     * 设置折扣策略
     * JSON格式:{ strategy：“XXX”, obj:{XXX}}
     * @param strategy 策略对象
     *
     */
    public void setStrategy(AbstractCouponStrategy strategy,CouponRulePo couponRule) {

        Map<String, Object> jsonObj = new HashMap<String, Object>(2);
        jsonObj.put("name", strategy.getClass().getName());
        jsonObj.put("obj",strategy);
        String jsonString = JacksonUtil.toJson(jsonObj);
        couponRule.setStrategy(jsonString);
    }


    /**
     * 计算使用优惠券后各明细的价格，返回新的明细
     * @param orderItems
     * @param couponId
     * @return
     */
    public List<OrderItem> calcDiscount(List<OrderItem> orderItems, Integer couponId) {
        System.out.println("aaa");
      List<OrderItem> validItems=getValidItems(orderItems, couponId);


      CouponPo coupon=couponMapper.findCouponById(couponId);

      System.out.println("bbb");

      //这里需要判断一下从用户已经领取的优惠券中使用
      BigDecimal totalPrice=BigDecimal.ZERO;

      //findCouponRuleById返回的的是CouponRulePo
      CouponRulePo couponRule=couponMapper.findCouponRuleById(coupon.getCouponRuleId());

      System.out.println("ccc");

      if(validItems.size()!=0)
      {
          List<OrderItem> newItems=this.getStrategy(couponRule).cacuDiscount(orderItems,couponId);
          System.out.println("ddd");
          return newItems;
      }
      else
      {
          return orderItems;
      }

    }

    /**
     * 根据优惠券规则id更改coupon的状态
     * @param id
     */

    public int updateCouponStatus(Integer id) {
        return couponMapper.updateCouponStatus(id);
    }


    /**
     * 获取用户可见优惠券规则
     * @return List<CouponRulePo>
     */
    public List<CouponRulePo> findUserCouponRules() {
        List<CouponRulePo> allCouponRules = couponMapper.findCouponRulesAvailable();
        LocalDateTime now = LocalDateTime.now();
        Iterator<CouponRulePo> iterator = allCouponRules.iterator();
        while (iterator.hasNext()){
            CouponRulePo next = iterator.next();
            if (now.isAfter(next.getBeginTime()) && now.isBefore(next.getEndTime())){
                allCouponRules.add(next);
            }
        }

        return allCouponRules;
    }

    /**
     * 用户使用优惠券后修改状态
     * @param userId
     * @param couponId
     * @return
     */
    public int updateUserCouponStatus(Integer userId, Integer couponId) {
        CouponPo couponPo=new CouponPo();
        couponPo.setId(couponId);
        couponPo.setStatusCode(1);
        couponPo.setUsedTime(LocalDateTime.now());
        couponPo.setUserId(userId);
        return couponMapper.updateUserCouponStatus(couponPo);
    }

    /**
     * 用户查看优惠券规则
     * @return
     */
    public List<CouponRulePo> getUserCouponRules() {
        List<CouponRulePo> userList=new ArrayList<>();
        List<CouponRulePo> rulePos=couponMapper.getCouponList();
        if(rulePos.size()==0){
            return null;
        }
        for(CouponRulePo rule:rulePos){
            LocalDateTime now=LocalDateTime.now();
            LocalDateTime beginTime=rule.getBeginTime();
            LocalDateTime endTime=rule.getEndTime();
            boolean inTime=now.isAfter(beginTime)&&now.isBefore(endTime);
            Boolean statusCode=rule.getStatusCode();
            Boolean isDeleted=rule.getBeDeleted();
            if(inTime&&statusCode&&!isDeleted){
                userList.add(rule);
            }
        }
        return userList;
    }

    /**
     * 修改优惠券规则里面的领取数
     * @param couponRuleId
     * @return
     */
    public int modifiedCouponRuleNum(Integer couponRuleId) {
        return couponMapper.modifiedCouponRuleNum(couponRuleId);
    }

    public int invalidate(Integer couponRuleId) {
        return couponMapper.invalidate(couponRuleId);
    }

    public int invalidateCoupons(Integer couponRuleId) {
        return 0;
    }

    public List<CouponPo> getCouponsByRuleId(Integer couponRuleId){
        List<CouponPo> list=couponMapper.getCouponsByRuleId(couponRuleId);
        return list;
    }

    //根据coupon的id下架coupon
    public int invalidateCouponById(Integer id){
        return couponMapper.invalidateCoupon(id);
    }

    /**
     * 根据showType的类型查找优惠券
     * @param userId
     * @param showType
     * 0未使用，1已使用，2已失效，3已过期
     * @return
     */
    public List<CouponPo> showTypeCouponList(Integer userId, Integer showType) {

        List<CouponPo> userAllCoupons = couponMapper.getCouponMyList(userId);
        List<CouponPo> unUsedCoupons = new ArrayList<>();
        List<CouponPo> usedCoupons = new ArrayList<>();
        List<CouponPo> invalidCoupons = new ArrayList<>();
        List<CouponPo> expiredCoupons = new ArrayList<>();
        for (CouponPo couponPo : userAllCoupons) {
            Integer statusCode = couponPo.getStatusCode();
            if (statusCode == 0) {
                unUsedCoupons.add(couponPo);
            }
            if (statusCode == 1) {
                usedCoupons.add(couponPo);
            }
            if (statusCode == 2) {
                usedCoupons.add(couponPo);
            }
            LocalDateTime endTime = couponPo.getEndTime();
            LocalDateTime nowTime = LocalDateTime.now();
            if (nowTime.isAfter(endTime)) {
                expiredCoupons.add(couponPo);
            }
        }
        if (showType == 0) {
            //未使用
            return unUsedCoupons;
        }
        if (showType == 1) {
            //已使用
            return usedCoupons;
        }
        if (showType == 2) {
            //已失效
            return invalidCoupons;
        }
        if (showType == 4) {
            //已过期
            return expiredCoupons;
        }
        //没有其他的选项
        return null;
    }

}
