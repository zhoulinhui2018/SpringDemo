package xmu.oomall.topic.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xmu.oomall.OoMallApplication;

@SpringBootTest(classes = OoMallApplication.class)
public class TopicDaoTest {
    @Autowired
    private  TopicDao topicDao;

//    @Test//添加专题测试
//    public void addTopicTest() {
//
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
////        topicDao.adminAddTopic(newtopic);
//    }
//
//    @Test//更新专题测试
//    public void updateTopic(){
//        Topic newtopic = new Topic();
//        newtopic.setId(100003);
//        List<String> pic_urls = new ArrayList<String>();
//        newtopic.setGmtCreate(LocalDateTime.now());
//        newtopic.setGmtModified(LocalDateTime.now());
//        newtopic.setContent("new_new_content");
//        newtopic.setDeleted(true);
//        pic_urls.add("pic4_url");
//        pic_urls.add("pic5_url");
//        pic_urls.add("pic6_url");
//        newtopic.setPictures(pic_urls);
//        topicDao.adminUpdateTopicById(newtopic);
//    }
//
//
//    @Test
//    public void JsonTest(){
//        Topic topic = new Topic();
//        List<String> pic_urls = new ArrayList<String>();
//        pic_urls.add("pic4_url");
//        pic_urls.add("pic5_url");
//        pic_urls.add("pic6_url");
//        topic.setPictures(pic_urls);
//        System.out.println(topic.getPicUrlList());
//    }
//
//    //删除专题测试
//    @Test
//    public void deleteTopic(){
//        topicDao.adminDeleteTopicById(100002);
//    }
}
