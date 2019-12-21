package xmu.oomall.log.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xmu.oomall.log.domain.Log;
import xmu.oomall.log.service.impl.LogService;
import xmu.oomall.log.util.ResponseUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("")
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
     * @param page
     * @param limit
     * @param adminId
     * @return java.lang.Object.List
     * @author Ren tianhe
     * @date 2019/12/12
     */
    @GetMapping("/logs")
    public Object findLogListByAdminId(@RequestParam(defaultValue = "1") Integer page,
                                       @RequestParam(defaultValue = "10") Integer limit,
                                       @RequestParam Integer adminId,
                                       HttpServletRequest request){
        if(adminId<=0){
            return ResponseUtil.fail(580,"参数不合法");
        }
        if(page<=0||limit<=0){
            return ResponseUtil.fail(580,"参数不合法");
        }
        String adminid= request.getHeader("id");
        if (adminid==null){
            return ResponseUtil.unlogin();
        }
        Log log=new Log();
        log.setAdminId(Integer.valueOf(adminid));
        log.setIp(request.getRemoteAddr());
        log.setType(0);
        log.setStatusCode(1);
        log.setActions("查看日志列表");

        Log newlog = new Log();
        newlog.setId(adminId);
        List<Log> logList;
        try {
            logList = logService.findLogListByAdminId(page, limit, newlog);
        }catch (Exception e){
            log.setStatusCode(0);
            logService.addLog(log);
            return ResponseUtil.fail(901,"查看日志失败");
        }
        return ResponseUtil.ok(logList);
    }

    @PostMapping("/logs")
    public Object addLog(@RequestBody Log newlog){
        System.out.println("telalllalalalallala");
        logService.addLog(newlog);
        return ResponseUtil.ok();
    }
}
