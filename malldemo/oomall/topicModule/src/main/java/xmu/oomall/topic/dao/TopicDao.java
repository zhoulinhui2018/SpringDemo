package xmu.oomall.topic.dao;

import net.sf.json.JSONArray;
import net.sf.jsqlparser.statement.select.Top;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import xmu.oomall.topic.domain.*;
import xmu.oomall.topic.mapper.TopicMapper;
import xmu.oomall.topic.util.MallException;
import xmu.oomall.topic.util.ResponseUtil;

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
    public List<Topic> findTopicList() throws MallException{
        List<TopicPo> topicPos = topicMapper.findTopicList();//从数据库中获取topicpo对象
        List<Topic> topics = new ArrayList<Topic>();
        for(TopicPo item:topicPos){
            if(item == null)
                throw new MallException(ResponseUtil.badArgumentValue());
            //如果返回的是be_deleted=1的字段
            if(!item.getDeleted())
                throw new MallException(ResponseUtil.badArgumentValue());
            Topic temp = new Topic(item);//新建一个topic对象，修改构造方法
            temp.getPictures();
            topics.add(temp);
        }
        return topics;
    }

    //管理员添加专题
    /*
    错误的情况可能有：
    1. 由于mybatis出错导致，并没有成功添加进数据库
     */
    public Integer adminAddTopic(Topic newtopic) throws MallException{
        TopicPo topicPo = new TopicPo();
        topicPo.setGmtCreate(LocalDateTime.now());
        topicPo.setGmtModified(LocalDateTime.now());
        topicPo.setContent(newtopic.getContent());
        topicPo.setDeleted(false);
        newtopic.setPictures(newtopic.getpictures());//调用setpictures的方法，用newtopic中的pictures这个list，把private属性中的po对象内的pic_url_list给初始化
        topicPo.setPicUrlList(newtopic.getPicUrlList());
        if(topicMapper.adminAddTopic(topicPo)==0) {
            throw new MallException(ResponseUtil.updatedDataFailed());
        }
        return topicMapper.adminAddTopic(topicPo);
    }

    //管理员用户查看专题详情（通过id）
    /*
    错误的情况可能有：
    1. 由于mybatis出错导致，从数据库中查出来的内容为空
    2. 从数据库中查到的是be_deleted=1的数据
     */
    public Topic findTopicById(Integer id) throws MallException {
        TopicPo topicPo = topicMapper.findTopicById(id);
        Topic topic = new Topic(topicPo);
        topic.getPictures();
        //如果返回的是空的数据
        if(topic == null)
            throw new MallException(ResponseUtil.badArgumentValue());
        //如果返回的是be_deleted=1的字段
        if(!topic.getDeleted())
            throw new MallException(ResponseUtil.badArgumentValue());
        return topic;
    }

    //管理员编辑专题
    /*
    错误的情况可能有：
    1. 由于mybatis出错导致，数据库中的数据并没有被更新
    2. 传入的id在数据库中没有
    3. 修改的content和pictures为空
     */
    public Integer adminUpdateTopicById (Topic newtopic){
        TopicPo topicPo = new TopicPo();
        topicPo.setId(newtopic.getId());
        topicPo.setGmtModified(LocalDateTime.now());
        topicPo.setContent(newtopic.getContent());
        topicPo.setDeleted(newtopic.getDeleted());
        newtopic.setPictures(newtopic.getpictures());//调用setpictures的方法，把private属性中的po对象内的pic_url_list给初始化
        topicPo.setPicUrlList(newtopic.getPicUrlList());
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
