package xmu.oomall.topic.service;

import net.sf.jsqlparser.statement.select.Top;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xmu.oomall.topic.TopicApplication;
import xmu.oomall.topic.domain.Topic;
import xmu.oomall.topic.service.impl.TopicService;
import xmu.oomall.domain.*;
import xmu.oomall.topic.util.MallException;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = TopicApplication.class)
public class TopicServiceTest {

    @Autowired
    private TopicService topicService;

//    @Test
//    public List<Topic> admin_user_getTopicListTest() {
//        List<Topic> topics = new ArrayList<Topic>();
//        try {
//            topics = topicService.findTopicList(1, 2);
//            for (Topic item : topics) {
//                System.out.println(item);
//            }
//        } catch (MallException e) {
//
//        }
//        return topics;
//    }
}
