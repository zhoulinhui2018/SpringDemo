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

@Repository
public class TopicDao {
    @Autowired
    private TopicMapper topicMapper;

    //管理员/用户获得所有的专题（已重改）
    public List<Topic> findTopicList() {
        List<TopicPo> topicPos = topicMapper.findTopicList();//从数据库中获取topicpo对象
        List<Topic> topics = new ArrayList<Topic>();
        for(TopicPo item:topicPos){
            Topic temp = new Topic(item);
            temp.setPictures();
            topics.add(temp);
        }
        return topics;
    }

    //管理员添加专题（已重改）
    /*
    错误的情况可能有：
    1. 由于mybatis出错导致，并没有成功添加进数据库
     */
    public Integer adminAddTopic(TopicPo topicPo) throws MallException{
        topicPo.setGmtCreate(LocalDateTime.now());
        topicPo.setGmtModified(LocalDateTime.now());
        topicPo.setDeleted(false);
        return topicMapper.adminAddTopic(topicPo);
    }

    //管理员/用户查看专题详情（通过id）（已重改）
    /*
    错误的情况可能有：
    1. 由于mybatis出错导致，从数据库中查出来的内容为空
    2. 从数据库中查到的是be_deleted=1的数据
     */
    public Topic findTopicById(Integer id) throws MallException {
        TopicPo topicPo =topicMapper.findTopicById(id);
        if(topicPo == null){
            throw new MallException(ResponseUtil.serious());
        }
        Topic topic = new Topic(topicPo);
        return topic;
    }

    //管理员编辑专题
    /*
    错误的情况可能有：
    1. 由于mybatis出错导致，数据库中的数据并没有被更新
    2. 传入的id在数据库中没有
    3. 修改的content和pictures为空
     */
    public Integer adminUpdateTopicById (TopicPo topicPo){
        topicPo.setGmtModified(LocalDateTime.now());
        return topicMapper.adminUpdateTopicById(topicPo);
    }

    //管理员删除一个专题
    /*
    错误的情况可能有：
    1. 由于mybatis出错导致，数据库中的数据并没有被更新
    2. 传入的id在数据库中没有
     */
    public int adminDeleteTopicById(Integer id){
        return topicMapper.adminDeleteTopicById(id);
    }
}
