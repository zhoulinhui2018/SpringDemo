package xmu.oomall.address.util;

/**
 * MallException
 * @author: Zhang Yaqing
 * @date: 2019/12/12
 */
public class MallException extends Exception{
    private Object code = null;

    /**
     * MallException
     * @param object
     * @author: Zhang Yaqing
     * @date: 2019/12/12
     */
    public MallException(Object object){
        this.code = code;
    }


    public Object getErrorCode(){
        return code;
    }
}
