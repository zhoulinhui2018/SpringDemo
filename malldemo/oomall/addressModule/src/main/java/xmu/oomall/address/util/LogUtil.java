package xmu.oomall.address.util;

import xmu.oomall.address.domain.Log;

/**
 * IdUtil
 * @author: Zhang Yaqing
 * @date: 2019/12/12
 */
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
