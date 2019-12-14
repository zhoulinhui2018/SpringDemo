package xmu.oomall.topic.service.impl;

import com.github.pagehelper.PageHelper;
import net.sf.jsqlparser.statement.select.Top;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xmu.oomall.topic.dao.TopicDao;
import xmu.oomall.topic.domain.*;
import xmu.oomall.topic.service.ITopicService;

import java.util.List;

@Transactional
@Service
public class TopicService implements ITopicService{
    @Autowired
    private TopicDao topicDao;

    //重写接口中的方法

    @Override
    public List<Topic> findTopicList(Integer page, Integer limit){
        PageHelper.startPage(page,limit);
        return topicDao.findTopicList();
    }

    @Override
    public Topic adminAddTopic(Topic newtopic){
        topicDao.adminAddTopic(newtopic);
        return newtopic;
    }

    @Override
    public Topic findTopicById(Integer id){
        return topicDao.findTopicById(id);
    }

    //管理员更新专题信息
    @Override
    public Integer adminUpdateTopicById(Topic newtopic){
        return topicDao.adminUpdateTopicById(newtopic);
    }

    //管理员删除专题
    @Override
    public Integer adminDeleteTopicById(Integer id){
        return topicDao.adminDeleteTopicById(id);
    }
}
