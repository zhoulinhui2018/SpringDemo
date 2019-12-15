package xmu.oomall.topic.service.impl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import xmu.oomall.topic.domain.Log;
import xmu.oomall.topic.service.ILogService;

import java.util.List;

@Transactional
@Service
public class LogService implements ILogService{
    private static String logAPI= "http://localhost:8070/log";


    @Autowired
    RestTemplate restTemplate;
    //重写接口中的方法


    @Override
    public Integer addLog(Integer adminId, String ip, Integer type, Integer actionId, String action, Integer statusCode) {
        Log log = new Log();
        log.setAdminId(adminId);
        log.setIp(ip);
        log.setType(type);
        log.setActions(action);
        log.setStatusCode(statusCode);
        log.setActionId(actionId);
        MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("log", log);
        HttpEntity httpEntity = new HttpEntity(multiValueMap, null);
        restTemplate.postForObject(logAPI , httpEntity, Log.class);
        return 1;
    }
}
