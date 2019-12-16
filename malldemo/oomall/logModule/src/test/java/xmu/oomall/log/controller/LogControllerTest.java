package xmu.oomall.log.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import xmu.oomall.log.LogApplication;
import xmu.oomall.log.domain.Log;
import java.time.LocalDateTime;

@SpringBootTest(classes = LogApplication.class)
@AutoConfigureMockMvc
@Transactional
public class LogControllerTest {
    @Autowired
    private LogController logController;


    @Autowired
    private MockMvc mockMvc;

    //通过姓名找到对应的操作
//    @Rollback(false)
//    @Test
//    public void findloglistTest(){
//        String username="王";
//        Object object = logController.findLogListByAdminId(1,5,200001);
//        System.out.println(object.toString());
//    }

    @Rollback(false)
    @Test
    public void addLogTest(){
        Log log = new Log();
        log.setActions("删除一个商品");
        log.setActionId(100006);
        log.setType(3);
        log.setStatusCode(1);
        log.setIp("ip");
        log.setAdminId(100001);
        log.setGmtModified(LocalDateTime.now());
        log.setGmtCreate(LocalDateTime.now());
        logController.addLog(log);
    }
}
