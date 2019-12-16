package xmu.oomall.log.util;
import xmu.oomall.log.domain.*;

public class MallException extends Exception{
    private Object code = null;

    //获取状态码
    public MallException(Object object){
        this.code = code;
    }

    //得到错误状态码
    public Object getErrorCode(){
        return code;
    }
}
