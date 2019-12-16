package xmu.oomall.topic.service;

import org.springframework.stereotype.Service;
import xmu.oomall.topic.domain.Log;

import java.util.List;

/**
 * @Author: Ming Qiu
 * @Description: 商品有关的服务
 * @Date: Created in 15:47 2019/11/5
 * @Modified By:
 **/
@Service
public interface ILogService {
    Integer addLog(Integer adminId, String ip, Integer type, Integer actionId, String action, Integer statusCode);
}
