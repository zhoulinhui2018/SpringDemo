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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = LogApplication.class)
@AutoConfigureMockMvc
@Transactional
public class LogControllerTest {
    @Autowired
    private LogController logController;


    @Autowired
    private MockMvc mockMvc;

    //通过姓名找到对应的操作
    @Rollback(false)
    @Test
    public void findloglistTest(){
        String username="王";
        Object object = logController.findLogListByAdminName(1,5,username);
        System.out.println(object.toString());
    }

}
