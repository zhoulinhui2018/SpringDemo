package xmu.oomall.log.service.impl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import xmu.oomall.log.dao.LogDao;
import xmu.oomall.log.service.ILogService;
import xmu.oomall.log.domain.Log;
import java.util.List;

@Transactional
@Service
public class LogService implements ILogService{
    private static String logAPI= "http://localhost:8070/log";

    @Autowired
    private LogDao logDao;

    @Autowired
    RestTemplate restTemplate;
    //重写接口中的方法

    @Override
    public List<Log> findLogListByAdminName(Integer page, Integer limit, String username){
        PageHelper.startPage(page,limit);
        return logDao.findLogListByAdminName(username);
    }

    @Override
    public Integer addLog(Log newlog) {
        return logDao.addLog(newlog);
    }
}
