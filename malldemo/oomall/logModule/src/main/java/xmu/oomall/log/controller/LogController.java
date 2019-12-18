package xmu.oomall.log.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xmu.oomall.log.domain.Log;
import xmu.oomall.log.service.impl.LogService;
import xmu.oomall.log.util.ResponseUtil;

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
     *
     * @param
     * @Author Ren tianhe
     * @Date 2019/12/12
     */
    @GetMapping("/logs")
    public Object findLogListByAdminId(@RequestParam(defaultValue = "1") Integer page,
                                       @RequestParam(defaultValue = "10") Integer limit,Integer adminId){
//                                       HttpServletRequest request) {
//        if(request.getIntHeader("userId")==0){
//            return ResponseUtil.unlogin();
//        }
//        List<Log> loglist = logService.findLogListByAdminId(page,limit,request.getIntHeader("userId"));
        Log newlog = new Log();
        newlog.setId(adminId);
        List<Log> loglist = logService.findLogListByAdminId(page,limit,newlog);
        return ResponseUtil.ok(loglist);
    }

    @PostMapping("/logs")
    public Object addLog(@RequestBody Log newlog){
        System.out.println("telalllalalalallala");
        logService.addLog(newlog);
        return ResponseUtil.ok();
    }
}
