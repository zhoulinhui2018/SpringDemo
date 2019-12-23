package xmu.oomall.footprint.util;

/**
 * MallException
 * @author Zhang Yaqing
 * @date 2019/12/18
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
