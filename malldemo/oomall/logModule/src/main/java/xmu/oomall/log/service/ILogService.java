package xmu.oomall.log.service;

import org.springframework.stereotype.Service;
import xmu.oomall.log.domain.Log;

import java.util.List;

/**
 * @Author: Ming Qiu
 * @Description: 商品有关的服务
 * @Date: Created in 15:47 2019/11/5
 * @Modified By:
 **/
@Service
public interface ILogService {
    public List<Log> findLogListByAdminName(Integer page, Integer limit,String username);

    public Integer addLog(Log newlog);
}
