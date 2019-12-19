package xmu.oomall.topic.dao;

import net.sf.jsqlparser.statement.select.Top;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import xmu.oomall.OoMallApplication;
import xmu.oomall.topic.domain.Topic;
import xmu.oomall.topic.domain.TopicPo;
import xmu.oomall.topic.util.MallException;
import xmu.oomall.topic.util.ResponseUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = OoMallApplication.class)
public class TopicDaoTest {
    @Autowired
    private  TopicDao topicDao;

    //获得专题列表（已通过）
//    @Test
//    public void getAllTopicList(){
//            List<Topic> topics=topicDao.findTopicList();
//            for(Topic topic:topics){
//                System.out.println(topic);
//    }
//    }

//    @Test
//    public void findAdDetail(){
//        try{
//            Topic topic = topicDao.findTopicById(200005);
//            System.out.println(topic);
//        }
//        catch (Exception e){
//
//        }
//    }
//
//    @Rollback(false)
//    @Test//添加专题测试（已通过）
//    public void addTopicTest() {
//        TopicPo newtopic = new TopicPo();
//        newtopic.setGmtCreate(LocalDateTime.now());
//        newtopic.setGmtModified(LocalDateTime.now());
//        newtopic.setContent("content");
//        newtopic.setDeleted(false);
//        newtopic.setPicUrlList("{\"pictures\":[\"pic7_url\",\"pic8_url\",\"pic9_url\"]}");
//        try
//        {topicDao.adminAddTopic(newtopic);}
//        catch (MallException e){
//
//        }
//    }
//
//    @Test//更新专题测试（已通过）
//    public void updateTopic(){
//        TopicPo newtopic = new TopicPo();
//        newtopic.setId(100003);
//        List<String> pic_urls = new ArrayList<String>();
//        newtopic.setGmtCreate(LocalDateTime.now());
//        newtopic.setGmtModified(LocalDateTime.now());
//        newtopic.setContent("new_new_content");
//        newtopic.setDeleted(false);
//        topicDao.adminUpdateTopicById(newtopic);
//    }
//    //删除专题测试（已通过）
//    @Test
//    public void deleteTopic(){
//        topicDao.adminDeleteTopicById(100003);
//    }
}
