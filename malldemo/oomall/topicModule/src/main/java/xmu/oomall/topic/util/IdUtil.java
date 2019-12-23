package xmu.oomall.topic.util;

import java.util.Random;

/**
 * 各种id生成策略
 *
 * @version 1.0
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
