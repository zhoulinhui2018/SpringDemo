package xmu.oomall.log;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
    public class LogApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(LogApplication.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
