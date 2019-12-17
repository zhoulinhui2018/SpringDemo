package xmu.oomall.address.util;

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
