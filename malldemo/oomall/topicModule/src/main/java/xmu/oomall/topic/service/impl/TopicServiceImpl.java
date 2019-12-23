package xmu.oomall.topic.service.impl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import xmu.oomall.topic.dao.TopicDao;
import xmu.oomall.topic.domain.Log;
import xmu.oomall.topic.domain.Topic;
import xmu.oomall.topic.domain.TopicPo;
import xmu.oomall.topic.service.ITopicService;
import xmu.oomall.topic.util.MallException;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Topic模块
 * @author Ren tianhe
 * @date 2019/12/17
 */
@Service
public class TopicServiceImpl implements ITopicService{
    @Autowired
    private TopicDao topicDao;

    @Autowired
    RestTemplate restTemplate;


    @Override
    public List<Topic> findTopicList(Integer page, Integer limit) throws Exception{
        PageHelper.startPage(page,limit);
        return topicDao.findTopicList();
    }

    @Override
    public Integer adminAddTopic(TopicPo topicPo) throws Exception{
        return topicDao.adminAddTopic(topicPo);
    }

    @Override
    public Topic findTopicById(Integer id) throws Exception {
        return topicDao.findTopicById(id);
    }


    @Override
    public Integer adminUpdateTopicById(TopicPo topicPo) throws Exception {
        return topicDao.adminUpdateTopicById(topicPo);
    }


    @Override
    public Integer adminDeleteTopicById(Integer id) throws Exception{
        TopicPo topicPo = new TopicPo();
        topicPo.setId(id);
        topicDao.adminUpdateTopicById(topicPo);
        return topicDao.adminDeleteTopicById(id);
    }
}
