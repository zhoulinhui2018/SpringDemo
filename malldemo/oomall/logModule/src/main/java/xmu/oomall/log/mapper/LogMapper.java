package xmu.oomall.log.mapper;

import org.apache.ibatis.annotations.Mapper;
import xmu.oomall.log.domain.Log;
import java.util.List;

/**
 * Topic模块
 * @author Ren tianhe
 * @date 2019/12/17
 */
@Mapper
public interface LogMapper {

    /**
     * 管理员根据条件查看日志
     * @param newlog
     * @return List<Log>
     * @author Ren tianhe
     * @date 2019/12/12
     */
    public List<Log> findLogListByAdminId(Log newlog);

    /**
     * 新增日志
     * @param newlog
     * @return Integer
     * @author Ren tianhe
     * @date 2019/12/12
     */
    public Integer addLog(Log newlog);
}