package xmu.oomall.log.mapper;

import org.apache.ibatis.annotations.Mapper;
import xmu.oomall.log.domain.Log;
import java.util.List;

@Mapper
public interface LogMapper {
    public List<Log> findLogListByAdminId(Log newlog);

    public Integer addLog(Log newlog);
}