package xmu.oomall.discount.service.impl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import xmu.oomall.discount.controller.vo.PresaleRuleVo;
import xmu.oomall.discount.dao.PresaleDao;
import xmu.oomall.discount.domain.*;
import xmu.oomall.discount.domain.Promotion.PresaleRule;
import xmu.oomall.discount.service.IPresaleServiceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class PresaleServiceImplImpl implements IPresaleServiceImpl {
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
        ServiceInstance instance = loadBalancerClient.choose("logService");
        String reqUrl = String.format("http://%s:%s", instance.getHost(), instance.getPort() + "/logs");
        restTemplate.postForObject(reqUrl,log,Log.class);
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
     * @param item
     * @return
     */
    @Override
    public PresaleRuleVo isPresaleOrder(OrderItem item){
        Integer goodsId=item.getProduct().getGoodsId();

        System.out.println("te");
        PresaleRule rule=presaleDao.isPresaleOrder(goodsId);
        if(rule==null){
            return null;
        }
        System.out.println("tes1");
        PresaleRuleVo ruleVo=new PresaleRuleVo();
        ruleVo.setPresaleRule(rule);
        GoodsPo goodsPo=item.getProduct().getGoods();
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
    public Boolean dealRefund(PresaleRule presaleRule) {
        RestTemplate restTemplate = new RestTemplate();
        ServiceInstance instance = loadBalancerClient.choose("orderService");
        String reqUrl = String.format("http://%s:%s", instance.getHost(), instance.getPort() + "/orders/presaleRule/refund");
        Boolean flag = restTemplate.postForObject(reqUrl,presaleRule,Boolean.class);
        return flag;
    }


    @Override
    public List<Payment> getPaymentList(List<Order> orderList) {
        //遍历orderList，生成payment，设置price
        List<Payment> paymentList=new ArrayList<>();
        for(int i=0;i<orderList.size();i++){
            Payment newPayment=new Payment();
            BigDecimal refund=orderList.get(i).getOrderItemList().get(0).getDealPrice();
            newPayment.setActualPrice(refund.negate());
            paymentList.add(newPayment);
        }
        return paymentList;
    }

    @Override
    public void presaleRefund(List<Payment> paymentList) {
        RestTemplate restTemplate = new RestTemplate();
        ServiceInstance instance = loadBalancerClient.choose("paymentService");
        String reqUrl = String.format("http://%s:%s", instance.getHost(), instance.getPort() + "/orders/presaleOrders/refund");
        restTemplate.getForObject(reqUrl,List.class,paymentList);
    }

    @Override
    public List<PresaleRuleVo> findPresaleRule(Integer goodsId, Integer page, Integer limit){
        PageHelper.startPage(page,limit);
        List<PresaleRuleVo> list=presaleDao.findByGoodsId(goodsId);
        return list;
    }
    @Override
    public List<PresaleRuleVo> findAllPresaleGoods(Integer page,Integer limit){
        PageHelper.startPage(page,limit);
        List<PresaleRuleVo> list=presaleDao.findAllPresaleGoods();
        return list;
    }

    @Override
    public List<PresaleRuleVo> findOnPresaleRules(Integer page, Integer limit){
        PageHelper.startPage(page,limit);
        List<PresaleRuleVo> list=presaleDao.findOnPresaleRules();
        return list;
    }

    @Override
    public int invalidate(Integer id) {
        return presaleDao.invalidate(id);
    }
}
