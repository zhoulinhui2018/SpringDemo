package xmu.oomall.log;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class LogApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(LogApplication.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
