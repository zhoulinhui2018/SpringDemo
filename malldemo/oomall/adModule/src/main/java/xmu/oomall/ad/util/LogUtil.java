package xmu.oomall.ad.util;

import xmu.oomall.ad.domain.Log;

public class LogUtil {
    public static Log newLog(String actions,Integer actionId,Integer adminId,Integer type,String ip){
        Log log=new Log();
        log.setStatusCode(0);
        log.setActions(actions);
        log.setActionId(actionId);
        log.setAdminId(adminId);
        log.setType(type);
        log.setIp(ip);
        return log;
    }
}