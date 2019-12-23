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
    /**
     * findLogListByAdminId
     * @param  page 1
     * @param  newlog 广告
     * @param  limit 1
     * @return List<Log>
     * @author Zhou Linhui
     * @date 2019/12/5
     */
    public List<Log> findLogListByAdminId(Integer page, Integer limit,Log newlog) throws Exception;
    /**
     * findLogListByAdminId
     * @param  newLog 广告
     * @author Zhou Linhui
     * @date 2019/12/5
     */
    void addLog(Log newLog);
}
