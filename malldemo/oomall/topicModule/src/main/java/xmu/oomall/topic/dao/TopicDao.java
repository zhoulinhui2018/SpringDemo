package xmu.oomall.topic.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import xmu.oomall.topic.domain.Topic;
import xmu.oomall.topic.domain.TopicPo;
import xmu.oomall.topic.mapper.TopicMapper;
import xmu.oomall.topic.util.MallException;
import xmu.oomall.topic.util.ResponseUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ren tianhe
 * @date 2019/12/21
 */
@Repository
public class TopicDao {
    @Autowired
    private TopicMapper topicMapper;


    public List<Topic> findTopicList() throws Exception {
        List<TopicPo> topicPos = topicMapper.findTopicList();
        List<Topic> topics = new ArrayList<Topic>();
        for(TopicPo item:topicPos){
            Topic temp = new Topic(item);
            temp.setPictures();
            topics.add(temp);
        }
        return topics;
    }


    public Integer adminAddTopic(TopicPo topicPo) throws Exception{
        topicPo.setGmtCreate(LocalDateTime.now());
        topicPo.setGmtModified(LocalDateTime.now());
        topicPo.setDeleted(false);
        return topicMapper.adminAddTopic(topicPo);
    }


    public Topic findTopicById(Integer id)  throws Exception{
        TopicPo topicPo =topicMapper.findTopicById(id);
        Topic topic = new Topic(topicPo);
        topic.setPictures();
        return topic;
    }


    public Integer adminUpdateTopicById (TopicPo topicPo) throws Exception{
        topicPo.setGmtModified(LocalDateTime.now());
        return topicMapper.adminUpdateTopicById(topicPo);
    }


    public int adminDeleteTopicById(Integer id) throws Exception{
        return topicMapper.adminDeleteTopicById(id);
    }
}
