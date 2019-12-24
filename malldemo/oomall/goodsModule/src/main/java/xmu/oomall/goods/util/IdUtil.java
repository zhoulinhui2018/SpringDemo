package xmu.oomall.goods.util;

import java.util.Random;

/**
 * Demo class IdUtil
 *
 * @author Zhang Yaqing
 * @date 2019/12/20
 */
public class IdUtil {

    /**
     * 图片名生成
     */
    public static String genImageName() {
        long millis = System.currentTimeMillis();
        Random random = new Random();
        int end3 = random.nextInt(999);
        String str = millis + String.format("%03d", end3);
        return str;
    }
}
