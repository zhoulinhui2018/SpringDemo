package xmu.oomall.log.service.impl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xmu.oomall.log.dao.LogDao;
import xmu.oomall.log.service.ILogService;
import xmu.oomall.log.domain.Log;
import java.util.List;

@Transactional
@Service
public class LogService implements ILogService{
    @Autowired
    private LogDao logDao;

    //重写接口中的方法

    @Override
    public List<Log> findLogListByAdminName(Integer page, Integer limit, String username){
        PageHelper.startPage(page,limit);
        return logDao.findLogListByAdminName(username);
    }

    @Override
    public Integer addLog(Log newlog){
        return logDao.addLog(newlog);
    }
}
