package xmu.oomall.topic;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * TopicApplication
 * @author Zhang Yaqing
 * @date 2019/12/18
 */
@SpringBootApplication
@MapperScan("xmu.oomall.topic.mapper")
public class TopicApplication {

    public static void main(String[] args) {
        SpringApplication.run(TopicApplication.class, args);
    }

}
