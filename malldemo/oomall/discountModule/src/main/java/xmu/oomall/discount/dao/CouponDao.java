package xmu.oomall.discount.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import xmu.oomall.discount.domain.OrderItem;
import xmu.oomall.discount.domain.coupon.AbstractCouponStrategy;
import xmu.oomall.discount.domain.coupon.CouponPo;
import xmu.oomall.discount.domain.coupon.CouponRulePo;
import xmu.oomall.discount.domain.goods.GoodsInfo;
import xmu.oomall.discount.mapper.CouponMapper;
import xmu.oomall.discount.util.JacksonUtil;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;



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


    public List<CouponPo> getMyCoupons0(Integer userId){
        return couponMapper.getMyCoupons0(userId);
    }

    public List<CouponPo> getMyCoupons1(Integer userId){
        return couponMapper.getMyCoupons1(userId);
    }

    public List<CouponPo> getMyCoupons2(Integer userId){
        return couponMapper.getMyCoupons2(userId);
    }

    public List<CouponPo> getMyCoupons3(Integer userId){
        List<CouponPo> couponMyList = couponMapper.getCouponMyList(userId);
        Iterator<CouponPo> iterator = couponMyList.iterator();
        while (iterator.hasNext()){
            CouponPo next = iterator.next();
            if (next.getEndTime().isBefore(LocalDateTime.now())){
                iterator.remove();
            }
        }
        return couponMyList;
    }


    /**
     * 管理员增加优惠券规则
     * @param couponRule
     */
    public void addCouponRule(CouponRulePo couponRule) {
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
        return couponMapper.deleteCouponRuleById(id);
    }

    /**
     * 管理员更新优惠券规则
     * @param couponRule
     * @return
     */
    public int updateCouponRuleById(CouponRulePo couponRule) {
        return couponMapper.updateCouponRuleById(couponRule);
    }

    /**
     * 根据id搜索优惠券规则
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
    public List<CouponRulePo> getCouponRules(List<CouponPo> coupons)
    {
        List<CouponRulePo> rules=new ArrayList<CouponRulePo>();
        for(int i=0;i<coupons.size();i++)
        {
            CouponRulePo couponRule=couponMapper.getCouponRule(coupons.get(i).getCouponRuleId());
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
     */
    public void addCoupon(CouponPo coupon) {
        couponMapper.addCoupon(coupon);
    }


    /**
     * 获取可用优惠券列表
     * @param goodsIdList
     * @param userId
     * @return
     */
    public Set<CouponRulePo> getCanUsedCoupons(List<Integer> goodsIdList,Integer userId) {
        List<CouponPo> coupons=couponMapper.getCouponMyList(userId);
        List<CouponRulePo> couponRules=getCouponRules(coupons);
        Set<CouponRulePo> canUsedCoupons=new TreeSet<>();
        for(int i=0;i<goodsIdList.size();i++)
        {
            for(int j=0;j<couponRules.size();j++) {
                if(isUsedOnGoods(goodsIdList.get(i),couponRules.get(j))==true)
                {
                   canUsedCoupons.add(couponRules.get(j));
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
        Set<Integer> goodsIds=new TreeSet<>();
        goodsIds.clear();
        goodsIds.addAll(getGoodsIdsInCouponRule(couponRule));
        if(goodsIds.contains(GoodsInfo.allGoodsInfo.getId()))
        {
            return true;
        }

        else{
            return goodsIds.contains(goodsId);
        }
    }

    /**
     * 获取优惠券中适用的所有商品
     * @param
     * @param couponRule
     * @return
     */
    public List<Integer> getGoodsIdsInCouponRule(CouponRulePo couponRule){

            String jsonString = couponRule.getGoodsList1() + "," + couponRule.getGoodsList2();
            List<Integer> goodsIds = Arrays.asList(jsonString.split(",")).stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
            return goodsIds;

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
        CouponRulePo couponRule=couponMapper.findCouponRuleById(coupon.getCouponRuleId());
        for (OrderItem item: items){
            //根据orderitem->product->GoodsPo->GoodsId
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
      List<OrderItem> validItems=getValidItems(orderItems, couponId);
      CouponPo coupon=couponMapper.findCouponById(couponId);
      BigDecimal totalPrice=BigDecimal.ZERO;
      CouponRulePo couponRule=couponMapper.findCouponRuleById(coupon.getCouponRuleId());

      if(validItems.size()!=0)
      {
          List<OrderItem> newItems=this.getStrategy(couponRule).cacuDiscount(orderItems,couponId);
          //System.out.println(newItems);
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
}
