package xmu.oomall.log;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
