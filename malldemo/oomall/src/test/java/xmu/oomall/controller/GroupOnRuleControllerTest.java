package xmu.oomall.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import xmu.oomall.OoMallApplication;
import xmu.oomall.domain.goods.GroupOnRule;
import xmu.oomall.util.JacksonUtil;

import java.time.LocalDateTime;

@SpringBootTest(classes = OoMallApplication.class)
@AutoConfigureMockMvc
@Transactional
public class GroupOnRuleControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GroupOnRuleController groupOnRuleController;

    @Rollback(false)
    @Test
    public void addTest(){
        GroupOnRule groupOnRule=new GroupOnRule();
        groupOnRule.setBeDeleted(false);
        groupOnRule.setProductId(100001);
        groupOnRule.setStartTime(LocalDateTime.now());
        groupOnRule.setEndTime(LocalDateTime.now());
        groupOnRule.setGmtCreate(LocalDateTime.now());
        groupOnRule.setGmtModified(LocalDateTime.now());
        groupOnRule.setStatusCode(true);
        groupOnRule.setGrouponLevelStragety("level_top");

        String json = JacksonUtil.toJson(groupOnRule);

//        String responseString=this.mockMvc.perform(PostMapping)
        groupOnRuleController.create(groupOnRule);
    }
}
