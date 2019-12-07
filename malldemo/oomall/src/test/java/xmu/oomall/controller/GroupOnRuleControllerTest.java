package xmu.oomall.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import xmu.oomall.OoMallApplication;
import xmu.oomall.domain.goods.GroupOnRule;
import xmu.oomall.util.JacksonUtil;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    public void addTest() throws Exception{
        GroupOnRule groupOnRule=new GroupOnRule();
        groupOnRule.setBeDeleted(false);
        groupOnRule.setProductId(100001);
        groupOnRule.setStartTime(LocalDateTime.now());
        groupOnRule.setEndTime(LocalDateTime.now());
        groupOnRule.setGmtCreate(LocalDateTime.now());
        groupOnRule.setGmtModified(LocalDateTime.now());
        groupOnRule.setStatusCode(true);
        groupOnRule.setGrouponLevelStrategy("level_top");

        String jsonString = JacksonUtil.toJson(groupOnRule);

        String responseString= this.mockMvc.perform(post("/grouponRules").contentType("application/json").content(jsonString))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andReturn().getResponse().getContentAsString();

        String errMsg = JacksonUtil.parseObject(responseString,"errmsg", String.class);
        Integer errNo = JacksonUtil.parseObject(responseString,"errno", Integer.class);
        GroupOnRule groupOnRule1 = JacksonUtil.parseObject(responseString,"data", GroupOnRule.class);

        assertEquals(errNo, 0);
        assertEquals(errMsg, "Success");
        assertEquals(groupOnRule.getProductId(),100001);
        assertEquals(groupOnRule.getGrouponLevelStrategy(),"level_top");
        //        groupOnRuleController.create(groupOnRule);
    }

    @Test
    public void updateTest() throws Exception {
        GroupOnRule groupOnRule=new GroupOnRule();
        groupOnRule.setBeDeleted(true);
        groupOnRule.setProductId(100001);
        groupOnRule.setStartTime(LocalDateTime.now());
        groupOnRule.setEndTime(LocalDateTime.now());
        groupOnRule.setGmtCreate(LocalDateTime.now());
        groupOnRule.setGmtModified(LocalDateTime.now());
        groupOnRule.setStatusCode(true);
        groupOnRule.setGrouponLevelStrategy("level_test");

        groupOnRuleController.update(100001,groupOnRule);
//        String jsonString = JacksonUtil.toJson(groupOnRule);
//
//        String responseString = this.mockMvc.perform(put("/grouponRules/{id}","100001").contentType(MediaType.APPLICATION_JSON).content(jsonString))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType("application/json"))
//                .andReturn().getResponse().getContentAsString();
//
//
//        String errMsg = JacksonUtil.parseObject(responseString,"errmsg", String.class);
//        Integer errNo = JacksonUtil.parseObject(responseString,"errno", Integer.class);
//        GroupOnRule groupOnRule1 = JacksonUtil.parseObject(responseString,"data", GroupOnRule.class);
//
//        assertEquals(errNo, 0);
//        assertEquals(errMsg, "Success");
//        assertEquals(groupOnRule.getProductId(),100001);
//        assertEquals(groupOnRule.getGrouponLevelStrategy(),"level_test");
    }

    @Test
    public void findTest() throws Exception {
//        String responseString = this.mockMvc.perform(put("/grouponRules/{id}","100001").contentType("application/json"))
//                .andExpect(content().contentType("application/json"))
//                .andReturn().getResponse().getContentAsString();
//
//
//        String errMsg = JacksonUtil.parseObject(responseString,"errmsg", String.class);
//        Integer errNo = JacksonUtil.parseObject(responseString,"errno", Integer.class);
//        GroupOnRule groupOnRule = JacksonUtil.parseObject(responseString,"data", GroupOnRule.class);
//
//        assertEquals(errNo, 0);
//        assertEquals(errMsg, "Success");
//        assertEquals(groupOnRule.getProductId(),100001);


        System.out.println(groupOnRuleController.detail(100001));
    }

    @Rollback(false)
    @Test
    public void deleteTest(){
        GroupOnRule groupOnRule=new GroupOnRule();
        groupOnRule.setId(100021);
        System.out.println(groupOnRuleController.delete(100001,groupOnRule));
    }
}