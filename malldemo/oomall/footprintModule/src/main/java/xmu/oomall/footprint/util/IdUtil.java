package xmu.oomall.footprint.util;

import java.util.Random;

/**
 * IdUtil
 * @author Zhang Yaqing
 * @date 2019/12/18
 */
public class IdUtil {

    /**
     * 图片名生成
     */
    public static String genImageName() {
        // 取当前时间的长整形值包含毫秒
        long millis = System.currentTimeMillis();
        Random random = new Random();
        int end3 = random.nextInt(999);
        // 如果不足三位前面补0
        String str = millis + String.format("%03d", end3);
        return str;
    }
}
