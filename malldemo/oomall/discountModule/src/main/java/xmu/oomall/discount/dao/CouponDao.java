package xmu.oomall.discount.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import xmu.oomall.discount.domain.CartItem;
import xmu.oomall.discount.domain.OrderItem;
import xmu.oomall.discount.domain.coupon.AbstractCouponStrategy;
import xmu.oomall.discount.domain.coupon.Coupon;
import xmu.oomall.discount.domain.coupon.CouponRule;
import xmu.oomall.discount.mapper.CouponMapper;
import xmu.oomall.domain.goods.Goods;
import xmu.oomall.util.JacksonUtil;

import java.math.BigDecimal;
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

    public void addCouponRule(CouponRule couponRule) {
        couponMapper.addCouponRule(couponRule);
    }


    public List<CouponRule> getCouponList() {
        List<CouponRule> couponRuleList=couponMapper.getCouponList();
        return couponRuleList;
    }

    public int deleteCouponRuleById(Integer id) {
        return couponMapper.deleteCouponRuleById(id);
    }

    public int updateCouponRuleById(CouponRule couponRule) {
        return couponMapper.updateCouponRuleById(couponRule);
    }

    public CouponRule findCouponRuleById(Integer id) {
        CouponRule couponRule=couponMapper.findCouponRuleById(id);
        return couponRule;
    }

    public List<Coupon> getCouponMyList(Integer userId) {
        List<Coupon> myList=couponMapper.getCouponMyList(userId);
        return myList;
    }

    public Integer getProductId(Integer itemId) {
        Integer productId=couponMapper.getProductId(itemId);
        return productId;
    }

    public Integer getGoodsId(Integer productId) {
        Integer goodsId=couponMapper.getGoodsId(productId);
        return goodsId;
    }

    /**
     * 通过用户的优惠券获取相应的优惠券规则
     * @param coupons
     * @return
     */
    public List<CouponRule> getCouponRules(List<Coupon> coupons)
    {
        List<CouponRule> rules=new ArrayList<CouponRule>();
        for(int i=0;i<coupons.size();i++)
        {
            CouponRule couponRule=couponMapper.getCouponRule(coupons.get(i).getCouponRuleId());
            rules.add(couponRule);
        }
        return rules;
    }

    /**
     * 获取可用优惠券列表
     * @param goodsIdList
     * @param userId
     * @return
     */
    public Set<CouponRule> getCanUsedCoupons(List<Integer> goodsIdList,Integer userId) {
        List<Coupon> coupons=couponMapper.getCouponMyList(userId);
        List<CouponRule> couponRules=getCouponRules(coupons);
        Set<CouponRule> canUsedCoupons=new TreeSet<>();
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
    private boolean isUsedOnGoods(Integer goodsId, CouponRule couponRule) {
        Set<Integer> goodsIds=new TreeSet<>();
        goodsIds.clear();
        goodsIds.addAll(getGoodsIdsInCouponRule(couponRule));
        if(goodsIds.contains(Goods.ALL_GOODS.getId()))
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
     * @return
     */

    public List<Integer> getGoodsIdsInCouponRule(CouponRule couponRule){

            String jsonString = couponRule.getGoodsList1() + "," + couponRule.getGoodsList2();
            //jsonString = org.apache.commons.text.StringEscapeUtils.unescapeJson(jsonString);
            //System.out.println("jsonString:"+jsonString);
            //return JacksonUtil.parseIntegerList(jsonString, "gIDs");
            List<Integer> goodsIds = Arrays.asList(jsonString.split(",")).stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
            //System.out.println(Arrays.toString(goodsIds .toArray()));
            return goodsIds;

    }

    public Coupon findCouponById(Integer id) {
        Coupon coupon=couponMapper.findCouponById(id);
        return coupon;
    }

    /**
     * 获得能用于此优惠卷的明细
     * @param items 订单明细
     * @return 适用的订单明细
     */
    private List<OrderItem> getValidItems(List<OrderItem> items,Integer couponId){
        System.out.println("getValidItems参数：items = "+items);
        List<OrderItem> validItems = new ArrayList<OrderItem>(items.size());
        Coupon coupon=couponMapper.findCouponById(couponId);
        CouponRule couponRule=couponMapper.findCouponRuleById(coupon.getCouponRuleId());
        for (OrderItem item: items){
            Integer productId = item.getProductId();
            Integer goodsId=couponMapper.getGoodsId(productId);
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
    public AbstractCouponStrategy getStrategy(CouponRule couponRule) {
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
    public void setStrategy(AbstractCouponStrategy strategy,CouponRule couponRule) {

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
      Coupon coupon=couponMapper.findCouponById(couponId);
      BigDecimal totalPrice=BigDecimal.ZERO;
      CouponRule couponRule=couponMapper.findCouponRuleById(coupon.getCouponRuleId());
      if(validItems.size()!=0)
      {
          List<OrderItem> newItems=this.getStrategy(couponRule).cacuDiscount(orderItems,couponId);
          System.out.println(newItems);
          return newItems;
      }
      else
      {
          return orderItems;
      }

    }
}
