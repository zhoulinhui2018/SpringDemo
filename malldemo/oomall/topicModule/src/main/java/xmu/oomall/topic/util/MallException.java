package xmu.oomall.topic.util;
import xmu.oomall.topic.domain.*;
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
