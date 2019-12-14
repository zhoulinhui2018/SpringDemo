package xmu.oomall.topic.dao;

import net.sf.json.JSONArray;
import net.sf.jsqlparser.statement.select.Top;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import xmu.oomall.topic.domain.*;
import xmu.oomall.topic.mapper.TopicMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@Repository
public class TopicDao {
    @Autowired
    private TopicMapper topicMapper;

    //管理员用户获得所有的专题
    public List<Topic> findTopicList(){
        List<TopicPo> topicPos = topicMapper.findTopicList();//从数据库中获取topicpo对象
        List<Topic> topics = new ArrayList<Topic>();
        for(TopicPo item:topicPos){
            Topic temp = new Topic(item);//新建一个topic对象，修改构造方法
            temp.getPictures();
            topics.add(temp);
        }
        return topics;
    }

    //管理员添加专题
    public Topic adminAddTopic(Topic newtopic){
        TopicPo topicPo = new TopicPo();
        topicPo.setGmtCreate(LocalDateTime.now());
        topicPo.setGmtModified(LocalDateTime.now());
        topicPo.setContent(newtopic.getContent());
        topicPo.setDeleted(false);
        newtopic.setPictures(newtopic.getpictures());//调用setpictures的方法，用newtopic中的pictures这个list，把private属性中的po对象内的pic_url_list给初始化
        topicPo.setPicUrlList(newtopic.getPicUrlList());
        topicMapper.adminAddTopic(topicPo);
        return newtopic;
    }

    //管理员用户查看专题详情（通过id）
    public Topic findTopicById(Integer id){
        TopicPo topicPo = topicMapper.findTopicById(id);
        Topic topic = new Topic(topicPo);
        topic.getPictures();
        return topic;
    }

    //管理员编辑专题
    public Integer adminUpdateTopicById(Topic newtopic){
        TopicPo topicPo = new TopicPo();
        topicPo.setId(newtopic.getId());
        topicPo.setGmtModified(LocalDateTime.now());
        topicPo.setContent(newtopic.getContent());
        topicPo.setDeleted(newtopic.getDeleted());
        newtopic.setPictures(newtopic.getpictures());//调用setpictures的方法，把private属性中的po对象内的pic_url_list给初始化
        topicPo.setPicUrlList(newtopic.getPicUrlList());
        return topicMapper.adminUpdateTopicById(topicPo);
    }

    public int adminDeleteTopicById(Integer id){
        return topicMapper.adminDeleteTopicById(id);
    }
}
