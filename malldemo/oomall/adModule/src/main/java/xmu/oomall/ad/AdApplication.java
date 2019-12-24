package xmu.oomall.ad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Demo class AdApplication
 *
 * @author Zhang Yaqing
 * @date 2019/12/20
 */
@EnableEurekaClient
@SpringBootApplication
public class AdApplication {

    public static void main(String[] args) {

        SpringApplication.run(AdApplication.class, args);
    }

}
