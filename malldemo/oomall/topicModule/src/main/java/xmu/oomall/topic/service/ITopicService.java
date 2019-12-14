package xmu.oomall.topic.service;

import net.sf.jsqlparser.statement.select.Top;
import org.springframework.stereotype.Service;
import xmu.oomall.topic.domain.*;

import java.util.List;

/**
 * @Author: Ming Qiu
 * @Description: 商品有关的服务
 * @Date: Created in 15:47 2019/11/5
 * @Modified By:
 **/
@Service
public interface ITopicService {
    public List<Topic> findTopicList(Integer page, Integer limit);

    public Topic adminAddTopic(Topic newtopic);

    public Topic findTopicById(Integer id);

    public Integer adminUpdateTopicById(Topic newtopic);

    public Integer adminDeleteTopicById(Integer id);
}
