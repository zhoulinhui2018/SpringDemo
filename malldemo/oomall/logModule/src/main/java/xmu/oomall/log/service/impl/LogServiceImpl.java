package xmu.oomall.log.service.impl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import xmu.oomall.log.dao.LogDao;
import xmu.oomall.log.service.ILogService;
import xmu.oomall.log.domain.Log;
import java.util.List;

/**
 * LogServiceImpl
 * @author Ren tianhe
 * @date 2019/12/17
 */
@Service
public class LogServiceImpl implements ILogService{
    private static String logAPI= "http://localhost:8803/log";

    @Autowired
    private LogDao logDao;

    @Autowired
    RestTemplate restTemplate;


    @Override
    public List<Log> findLogListByAdminId(Integer page, Integer limit, Log newlog) throws Exception{
        PageHelper.startPage(page,limit);
        return logDao.findLogListByAdminId(newlog);
    }

    @Override
    public void addLog(Log newLog) {
        logDao.addLog(newLog);
    }
}
