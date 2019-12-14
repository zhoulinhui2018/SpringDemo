package xmu.oomall.log.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xmu.oomall.log.domain.Log;
import xmu.oomall.log.service.impl.LogService;
import xmu.oomall.util.ResponseUtil;

import java.util.List;


@RestController
@RequestMapping("/logService")
public class LogController {
    @Autowired
    private LogService logService;

    /**
     * 管理员根据条件查看日志
     *
     * @param
     * @Author Ren tianhe
     * @Date 2019/12/12
     */
    @GetMapping("/")
    public Object findLogListByAdminName(@RequestParam(defaultValue = "1") Integer page,
                                         @RequestParam(defaultValue = "10") Integer limit,
                                         @RequestParam String username) {

        List<Log> loglist = logService.findLogListByAdminName(page,limit,username);
        return ResponseUtil.ok(loglist);
    }

    @PostMapping("/log")
    public Object addLog(Log newlog){
        logService.addLog(newlog);
        return ResponseUtil.ok();
    }

}
