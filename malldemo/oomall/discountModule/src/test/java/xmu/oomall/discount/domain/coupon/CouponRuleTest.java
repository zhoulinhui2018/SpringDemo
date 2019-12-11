package xmu.oomall.discount.domain.coupon;



//@SpringBootTest(classes = DiscountApplication.class)
class CouponRuleTest {
//    @Autowired
//    private CouponRule couponRule;

//
//    @BeforeEach
//    void setUp() {
//        //构造初始数据
//        CouponRulePo realObj = new CouponRulePo();
//        realObj.setStrategy("{\"name\":\"xmu.oomall.domain.coupon.CashOffStrategy\", \"obj\":{\"threshold\":100.01, \"offCash\":10.01}}");
//        realObj.setGoodsIds("{\"gIDs\":[1,2,3]}");
//        this.coupon = new CouponRule(realObj);
//    }
//
//    @Test
//    void getStrategy() {
//        CashOffStrategy eptStrategy = new CashOffStrategy(BigDecimal.valueOf(100.01),BigDecimal.valueOf(10.01));
//        AbstractCouponStrategy strategy = this.coupon.getStrategy();
//        assertEquals(eptStrategy, strategy);
//        assertEquals(eptStrategy.getClass(), CashOffStrategy.class);
//    }
//
//    @Test
//    void setStrategy() {
//        CashOffStrategy eptStrategy = new CashOffStrategy(BigDecimal.valueOf(1000.01),BigDecimal.valueOf(10.01));
//        this.coupon.setStrategy(eptStrategy);
//        CouponRulePo realObj = coupon.getRealObj();
//        assertEquals(realObj.getStrategy(), "{\"name\":\"xmu.oomall.domain.coupon.CashOffStrategy\",\"obj\":{\"threshold\":1000.01,\"offCash\":10.01}}" );
//    }
//
//    @Test
//    void getGoodsIdsInCouponRule() {
//        String jsonString=this.coupon.getGoodsList1()+this.coupon.getGoodsList2();
//        jsonString = org.apache.commons.text.StringEscapeUtils.unescapeJson(jsonString);
//
//        List<Integer> goodsIds =JacksonUtil.parseIntegerList(jsonString, "gIDs");
//
//        assertEquals(goodsIds.get(0), 1);
//        assertEquals(goodsIds.get(1), 2);
//        assertEquals(goodsIds.get(2), 3);
//    }
//
//    @Test
//    void setGoodsIds() {
//        List<Integer> goodsIds = new ArrayList<Integer>(3);
//        goodsIds.add(Integer.valueOf(7));
//        goodsIds.add(Integer.valueOf(8));
//        goodsIds.add(Integer.valueOf(9));
//
//        this.coupon.setGoodsIds(goodsIds);
//        CouponRulePo realObj = this.coupon.getRealObj();
//        assertEquals(realObj.getGoodsIds(),"{\"gIDs\":[7,8,9]}");
//    }
//

}