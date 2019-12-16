package xmu.oomall.topic.service;

import net.sf.jsqlparser.statement.select.Top;
import org.springframework.stereotype.Service;
import xmu.oomall.topic.domain.*;
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
    public List<Topic> findTopicList(Integer page, Integer limit) throws MallException;

    public Integer adminAddTopic(Topic newtopic) throws MallException;

    public Topic findTopicById (Integer id) throws MallException;

    public Integer adminUpdateTopicById(Topic newtopic);

    public Integer adminDeleteTopicById(Integer id);
}
