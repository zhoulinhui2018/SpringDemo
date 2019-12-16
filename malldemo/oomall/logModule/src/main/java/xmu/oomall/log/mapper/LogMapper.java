package xmu.oomall.log.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;
import xmu.oomall.log.domain.Log;
import java.util.List;

@Service
@Mapper
public interface LogMapper {
    public List<Log> findLogListByAdminId(Log newlog);

    public Integer addLog(Log newlog);
}