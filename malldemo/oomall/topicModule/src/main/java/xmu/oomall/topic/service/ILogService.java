package xmu.oomall.topic.service;

import org.springframework.stereotype.Service;
import xmu.oomall.topic.domain.Log;

import java.util.List;

/**
 * @Author: Ren Tianhe
 * 添加日志
 * @Date: Created in 15:47 2019/11/5
 **/
@Service
public interface ILogService {
      /**
       * 添加日志
       * @param newlog
       * @return void
       * @author Ren tianhe
       * @date 2019/12/13
       */
      void addlog(Log newlog);
}
