package xmu.oomall.address.util;

import java.util.Random;

/**
 * IdUtil
 * @author: Zhang Yaqing
 * @date: 2019/12/12
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
