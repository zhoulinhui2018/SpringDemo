package xmu.oomall.topic.controller;

import net.sf.jsqlparser.statement.select.Top;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import xmu.oomall.topic.TopicApplication;
import xmu.oomall.topic.domain.Topic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = TopicApplication.class)
@AutoConfigureMockMvc
@Transactional
public class TopicControllerTest {
    @Autowired
    private TopicController topicController;


    @Autowired
    private MockMvc mockMvc;

    //管理员插入topic测试
//    @Rollback(false)
//    @Test
//    public void adminAddTopicTest(){
//        Topic newtopic = new Topic();
//        List<String> pic_urls = new ArrayList<String>();
//        newtopic.setGmtCreate(LocalDateTime.now());
//        newtopic.setGmtModified(LocalDateTime.now());
//        newtopic.setContent("content");
//        newtopic.setDeleted(false);
//        pic_urls.add("pic1_url");
//        pic_urls.add("pic2_url");
//        pic_urls.add("pic3_url");
//        newtopic.setPictures(pic_urls);
//        Object object = topicController.adminAddTopic(newtopic);
//    }

    //用户管理员查看所有专题测试，单页返回三个
    @Test
    public void findAllTopicTest(){
        Object object = topicController.findTopicList(1,3);
        System.out.println(object.toString());
    }

    //用户管理员查看专题详情测试
//    @Test
//    public void findTopicByIdTest(){
//        Object object = topicController.findTopicById(100003);
//        System.out.println(object.toString());
//    }

    //管理员更新专题测试
//    @Rollback(false)
//    @Test
//    public void adminUpdateTopicByIdTest(){
//        HttpServletRequest
//        Topic newtopic = new Topic();
//        List<String> pic_urls = new ArrayList<String>();
//        newtopic.setId(100005);
//        newtopic.setGmtModified(LocalDateTime.now());
//        newtopic.setContent("new_content");
//        newtopic.setDeleted(false);
//        pic_urls.add("pic7_url");
//        pic_urls.add("pic8_url");
//        pic_urls.add("pic9_url");
//        newtopic.setPictures(pic_urls);
//        Object object = topicController.adminUpdateTopicById(100005,newtopic);
//    }

    //管理员删除专题测试
//    @Rollback(false)
//    @Test
//    public void adminDeleteTopicTest(){
//        topicController.adminDeleteTopicById(300004);
//    }
}
