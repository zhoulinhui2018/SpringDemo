package xmu.oomall.log.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import xmu.oomall.log.domain.Log;
import xmu.oomall.log.mapper.LogMapper;

import java.util.List;

@Repository
public class LogDao {
    @Autowired
    private LogMapper logMapper;

    //传入username，先通过mapper层查user表，找到对于的userid，然后通过userid到log表中，返回loglist
    //ligmapper中的对应的接口，就是可以调用mapper文件里对应的xml语句
    public List<Log> findLogListByAdminName(String username){
        Integer userid = logMapper.findAdminIdByName(username);
        List<Log> loglist=logMapper.findLogListByAdminName(userid);
        return loglist;
    }

    public Integer addLog(Log newlog){
        return logMapper.addLog(newlog);
    }
}
