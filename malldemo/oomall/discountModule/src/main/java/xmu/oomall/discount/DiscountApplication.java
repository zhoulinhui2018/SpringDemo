package xmu.oomall.discount;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Demo class DiscountApplication
 *
 * @author Zhang Yaqing
 * @date 2019/12/20
 */
@EnableScheduling
@SpringBootApplication
public class DiscountApplication {

    public static void main(String[] args) {

        try {
            SpringApplication.run(DiscountApplication.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
