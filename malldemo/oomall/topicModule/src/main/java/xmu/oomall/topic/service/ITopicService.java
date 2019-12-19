package xmu.oomall.topic.service;

import org.springframework.stereotype.Service;
import xmu.oomall.topic.domain.Topic;
import xmu.oomall.topic.domain.TopicPo;
import xmu.oomall.topic.util.MallException;

import java.util.List;

/**
 * @Author: Ming Qiu
 * @Description: 商品有关的服务
 * @Date: Created in 15:47 2019/11/5
 * @Modified By:
 **/
@Service
public interface ITopicService {
    public List<Topic> findTopicList(Integer page, Integer limit) throws Exception;

    public Integer adminAddTopic(TopicPo topicPo) throws Exception;

    public Topic findTopicById (Integer id) throws Exception;

    public Integer adminUpdateTopicById(TopicPo topicPo) throws Exception;

    public Integer adminDeleteTopicById(Integer id) throws Exception ;
}
