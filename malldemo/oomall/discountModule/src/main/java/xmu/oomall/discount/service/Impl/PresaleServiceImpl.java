package xmu.oomall.discount.service.Impl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import xmu.oomall.discount.controller.vo.PresaleRuleVo;
import xmu.oomall.discount.dao.PresaleDao;
import xmu.oomall.discount.domain.GoodsPo;
import xmu.oomall.discount.domain.Log;
import xmu.oomall.discount.domain.Order;
import xmu.oomall.discount.domain.Payment;
import xmu.oomall.discount.domain.Promotion.PresaleRule;
import xmu.oomall.discount.service.IPresaleService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class PresaleServiceImpl implements IPresaleService {
    @Autowired
    private PresaleDao presaleDao;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    /**
     * 管理员操作日志
     * @param log
     */
    @Override
    public void log(Log log){
        RestTemplate restTemplate = new RestTemplate();
        ServiceInstance instance = loadBalancerClient.choose("Log");
        String reqURL = String.format("http://%s:%s", instance.getHost(), instance.getPort() + "/logs");
        restTemplate.postForObject(reqURL,log,Log.class);
    }

    /**
     * 管理员添加预售规则
     * @param presaleRule
     */
    @Override
    public Integer add(PresaleRule presaleRule) {
        return presaleDao.add(presaleRule);
    }

    /**
     * 根据id找到预售规则
     * @param id
     * @return
     */
    @Override
    public PresaleRuleVo findById(Integer id) {
        return presaleDao.findById(id);
    }

    /**
     * 管理员根据id删除预售规则
     * @param id
     * @return
     */
    @Override
    public int delete(Integer id) {
        return presaleDao.delete(id);
    }

    /**
     * 管理员更新预售规则
     * @param presaleRule
     * @return
     */
    @Override
    public int update(PresaleRule presaleRule) {
        return presaleDao.update(presaleRule);
    }

    /**
     * 判断是否是预售订单，是的话返回对应的presaleRule
     * @param goodsId
     * @return
     */
    @Override
    public PresaleRuleVo isPresaleOrder(Integer goodsId){
        PresaleRule rule= presaleDao.isPresaleOrder(goodsId);
        PresaleRuleVo ruleVo=new PresaleRuleVo();
        ruleVo.setPresaleRule(rule);
        RestTemplate restTemplate = new RestTemplate();
        ServiceInstance instance = loadBalancerClient.choose("Goods");
        String reqURL = String.format("http://%s:%s", instance.getHost(), instance.getPort() + "/goods/{id}");
        GoodsPo goodsPo= restTemplate.getForObject(reqURL, GoodsPo.class,goodsId);
        ruleVo.setGoodsPo(goodsPo);
        return ruleVo;
    }


    /**
     * 为order创建定金和尾款的两个payment
     * @param order
     * @param rule
     * @return
     */
    @Override
    public List<Payment> presalePayment(Order order,PresaleRule rule){
        return presaleDao.presalePayment(order,rule);
    }


    @Override
    public List<Order> getPresaleRuleOrders(PresaleRule presaleRule) {
        RestTemplate restTemplate = new RestTemplate();
        ServiceInstance instance = loadBalancerClient.choose("Order");
        String reqURL = String.format("http://%s:%s", instance.getHost(), instance.getPort() + "/orders/presaleOrders");
        List<Order> orderList = restTemplate.getForObject(reqURL,List.class,presaleRule);
        return orderList;
    }


    @Override
    public List<Payment> getPaymentList(List<Order> orderList) {
        //遍历orderList，生成payment，设置price
        List<Payment> PaymentList=new ArrayList<>();
        for(int i=0;i<orderList.size();i++){
            Payment newPayment=new Payment();
            BigDecimal refund=orderList.get(i).getOrderItemList().get(0).getDealPrice();
            newPayment.setActualPrice(refund.negate());
            PaymentList.add(newPayment);
        }
        return PaymentList;
    }

    @Override
    public void presaleRefund(List<Payment> paymentList) {
        RestTemplate restTemplate = new RestTemplate();
        ServiceInstance instance = loadBalancerClient.choose("Payment");
        String reqURL = String.format("http://%s:%s", instance.getHost(), instance.getPort() + "/orders/presaleOrders/refund");
        restTemplate.getForObject(reqURL,List.class,paymentList);
    }

    @Override
    public List<PresaleRuleVo> findPresaleRule(Integer goodsId, Integer page, Integer limit){
        PageHelper.startPage(page,limit);
        List<PresaleRuleVo> list=presaleDao.findByGoodsId(goodsId);
        return list;
    }
    @Override
    public List<PresaleRuleVo> findAllPresaleRules(Integer page,Integer limit){
        PageHelper.startPage(page,limit);
        List<PresaleRuleVo> list=presaleDao.findAllPresaleRules();
        return list;
    }

    @Override
    public List<PresaleRuleVo> findOnPresaleRules(Integer page, Integer limit){
        PageHelper.startPage(page,limit);
        List<PresaleRuleVo> list=presaleDao.findOnPresaleRules();
        return list;
    }
}
