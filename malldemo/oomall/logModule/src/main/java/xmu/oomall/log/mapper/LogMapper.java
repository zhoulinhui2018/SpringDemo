package xmu.oomall.log.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;
import xmu.oomall.log.domain.Log;
import java.util.List;

@Service
@Mapper
public interface LogMapper {
    public List<Log> findLogListByAdminName(Integer id);

    public Integer findAdminIdByName(String name);

    public Integer addLog(Log newlog);
}