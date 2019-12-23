package xmu.oomall.log.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import xmu.oomall.log.domain.Log;
import xmu.oomall.log.mapper.LogMapper;

import java.time.LocalDateTime;
import java.util.List;

/**
 * LogDao
 * @author Ren tianhe
 * @date 2019/12/17
 */
@Repository
public class LogDao {
    @Autowired
    private LogMapper logMapper;


    public List<Log> findLogListByAdminId(Log newlog) throws Exception{
        List<Log> loglist=logMapper.findLogListByAdminId(newlog);
        return loglist;
    }

    public Integer addLog(Log newlog){
        newlog.setGmtCreate(LocalDateTime.now());
        newlog.setGmtModified(LocalDateTime.now());
        System.out.println("[info]:LogDao:"+newlog);
        return logMapper.addLog(newlog);
    }
}
