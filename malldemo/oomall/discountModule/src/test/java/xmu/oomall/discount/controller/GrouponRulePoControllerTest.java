package xmu.oomall.discount.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import xmu.oomall.discount.DiscountApplication;
import xmu.oomall.discount.domain.GrouponRulePo;
import xmu.oomall.util.JacksonUtil;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

;

@SpringBootTest(classes = DiscountApplication.class)
@AutoConfigureMockMvc
@Transactional
public class GrouponRulePoControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private DiscountController groupOnRuleController;

    @Rollback(false)
    @Test
    public void addTest() throws Exception{
        GrouponRulePo grouponRulePo =new GrouponRulePo();
        grouponRulePo.setProductId(100001);
        grouponRulePo.setEndTime(LocalDateTime.now());
        grouponRulePo.setGrouponLevelStrategy("level_top");

        String jsonString = JacksonUtil.toJson(grouponRulePo);

        String responseString= this.mockMvc.perform(post("/grouponRules").contentType("application/json").content(jsonString))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andReturn().getResponse().getContentAsString();

        String errMsg = JacksonUtil.parseObject(responseString,"errmsg", String.class);
        Integer errNo = JacksonUtil.parseObject(responseString,"errno", Integer.class);
        GrouponRulePo grouponRulePo1 = JacksonUtil.parseObject(responseString,"data", GrouponRulePo.class);

        assertEquals(errNo, 0);
        assertEquals(errMsg, "Success");
        assertEquals(grouponRulePo.getProductId(),100001);
        assertEquals(grouponRulePo.getGrouponLevelStrategy(),"level_top");
        //        groupOnRuleController.create(grouponRulePo);
    }

    @Test
    public void updateTest() throws Exception {
        GrouponRulePo grouponRulePo =new GrouponRulePo();
        grouponRulePo.setProductId(100001);
        grouponRulePo.setStartTime(LocalDateTime.now());
        grouponRulePo.setEndTime(LocalDateTime.now());
        grouponRulePo.setGrouponLevelStrategy("level_test");

        groupOnRuleController.update(100001, grouponRulePo);
//        String jsonString = JacksonUtil.toJson(grouponRulePo);
//
//        String responseString = this.mockMvc.perform(put("/grouponRules/{id}","100001").contentType(MediaType.APPLICATION_JSON).content(jsonString))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType("application/json"))
//                .andReturn().getResponse().getContentAsString();
//
//
//        String errMsg = JacksonUtil.parseObject(responseString,"errmsg", String.class);
//        Integer errNo = JacksonUtil.parseObject(responseString,"errno", Integer.class);
//        GrouponRulePo groupOnRule1 = JacksonUtil.parseObject(responseString,"data", GrouponRulePo.class);
//
//        assertEquals(errNo, 0);
//        assertEquals(errMsg, "Success");
//        assertEquals(grouponRulePo.getProductId(),100001);
//        assertEquals(grouponRulePo.getGrouponLevelStrategy(),"level_test");
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
//        GrouponRulePo groupOnRule = JacksonUtil.parseObject(responseString,"data", GrouponRulePo.class);
//
//        assertEquals(errNo, 0);
//        assertEquals(errMsg, "Success");
//        assertEquals(groupOnRule.getProductId(),100001);


        System.out.println(groupOnRuleController.detail(100001));
    }

//    @Rollback(false)
    @Test
    public void deleteTest(){
        GrouponRulePo grouponRulePo =new GrouponRulePo();
        grouponRulePo.setId(100021);
        System.out.println(groupOnRuleController.delete(100001, grouponRulePo));
    }

    @Test
    public void searchGrouponGoodsTest(){
        Object list = groupOnRuleController.list(10, "100001", 1, 10);
        System.out.println(list);
    }
}