package xmu.oomall.discount.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import xmu.oomall.discount.dao.PresaleDao;
import xmu.oomall.discount.domain.Log;
import xmu.oomall.discount.domain.Order;
import xmu.oomall.discount.domain.OrderItem;
import xmu.oomall.discount.domain.Payment;
import xmu.oomall.discount.domain.Promotion.PresaleRule;
import xmu.oomall.discount.service.IPresaleService;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PresaleServiceImpl implements IPresaleService {
    @Autowired
    private PresaleDao presaleDao;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Override
    public void log(Log log){
        RestTemplate restTemplate = new RestTemplate();
        ServiceInstance instance = loadBalancerClient.choose("Log");
        System.out.println(instance.getHost());
        System.out.println(instance.getPort());
        String reqURL = String.format("http://%s:%s", instance.getHost(), instance.getPort() + "/logs");
        restTemplate.postForObject(reqURL,log,Log.class);
    }

    /**
     * 管理员添加预售规则
     * @param presaleRule
     */
    @Override
    public void add(PresaleRule presaleRule) {
        presaleDao.add(presaleRule);
    }

    /**
     * 根据id找到预售规则
     * @param id
     * @return
     */
    @Override
    public PresaleRule findById(Integer id) {
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
    public PresaleRule isPresaleOrder(Integer goodsId){
        return presaleDao.isPresaleOrder(goodsId);
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
}
