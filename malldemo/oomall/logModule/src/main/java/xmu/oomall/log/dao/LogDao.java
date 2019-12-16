package xmu.oomall.log.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import xmu.oomall.log.mapper.LogMapper;
import xmu.oomall.log.domain.Log;
import java.util.List;

@Repository
public class LogDao {
    @Autowired
    private LogMapper logMapper;

    //传入username，先通过mapper层查user表，找到对于的userid，然后通过userid到log表中，返回loglist
    //ligmapper中的对应的接口，就是可以调用mapper文件里对应的xml语句
    //错误的情况可能有：
    //未查询到结果
    public List<Log> findLogListByAdminId(Integer admin_id){
        List<Log> loglist=logMapper.findLogListByAdminId(admin_id);
        return loglist;
    }

    public Integer addLog(Log newlog){
        return logMapper.addLog(newlog);
    }
}
