package xmu.oomall.topic.service.impl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import xmu.oomall.topic.domain.Log;
import xmu.oomall.topic.service.ILogService;

import java.util.List;

/**
 * 调用log服务
 * @author Ren tianhe
 * @date 2019/12/17
 */
@Service
public class LogServiceImpl implements ILogService{
    private static String logAPI= "http://localhost:8803/logs";


    @Autowired
    RestTemplate restTemplate;
    //重写接口中的方法

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Override
    public void addlog(Log log) {
        RestTemplate restTemplate = new RestTemplate();
        ServiceInstance instance = loadBalancerClient.choose("logService");
        System.out.println(instance.getHost());
        System.out.println(instance.getPort());
        String reqUrl = String.format("http://%s:%s", instance.getHost(), instance.getPort() + "/logs");
        restTemplate.postForObject(reqUrl,log,Log.class);
    }
}
