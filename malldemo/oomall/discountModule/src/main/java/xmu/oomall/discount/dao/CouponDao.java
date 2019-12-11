package xmu.oomall.discount.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import xmu.oomall.discount.domain.coupon.Coupon;
import xmu.oomall.discount.domain.coupon.CouponRule;
import xmu.oomall.discount.mapper.CouponMapper;
import xmu.oomall.domain.goods.Goods;

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
        System.out.println("goodsId参数:");
        String jsonString=couponRule.getGoodsList1()+","+couponRule.getGoodsList2();
        //jsonString = org.apache.commons.text.StringEscapeUtils.unescapeJson(jsonString);
        //System.out.println("jsonString:"+jsonString);
        //return JacksonUtil.parseIntegerList(jsonString, "gIDs");
        List<Integer> goodsIds = Arrays.asList(jsonString.split(",")).stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
        //System.out.println(Arrays.toString(goodsIds .toArray()));
        return goodsIds;
    }
}
