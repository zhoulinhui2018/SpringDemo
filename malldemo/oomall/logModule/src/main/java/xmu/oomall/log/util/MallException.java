package xmu.oomall.log.util;

/**
 * MallException
 * @author Ren tianhe
 * @date 2019/12/17
 */
public class MallException extends Exception{
    private Object code = null;


    public MallException(Object object){
        this.code = code;
    }

    public Object getErrorCode(){
        return code;
    }
}
