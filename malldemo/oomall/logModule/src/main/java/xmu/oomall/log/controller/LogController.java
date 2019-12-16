package xmu.oomall.log.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xmu.oomall.log.service.impl.LogService;
import xmu.oomall.util.ResponseUtil;
import xmu.oomall.log.domain.Log;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequestMapping("/logs")
/**
 * LogController
 * @Author Ren tianhe
 * @Date 2019/12/16
 */
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
    @GetMapping("")
    public Object findLogListByAdminId(@RequestParam(defaultValue = "1") Integer page,
                                         @RequestParam(defaultValue = "10") Integer limit,
                                       HttpServletRequest request) {
        if(request.getIntHeader("userId")==0){
            return ResponseUtil.unlogin();
        }
        List<Log> loglist = logService.findLogListByAdminId(page,limit,request.getIntHeader("userId"));
        return ResponseUtil.ok(loglist);
    }

    @PostMapping("")
    public Object addLog(Log newlog){
        logService.addLog(newlog);
        return ResponseUtil.ok();
    }
}
