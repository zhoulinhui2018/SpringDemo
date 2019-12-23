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

    /**
     * 用户查看专题列表
     * @param page
     * @param limit
     * @throws Exception
     * @return List<TopicPo>
     * @author Ren tianhe
     * @date 2019/12/13
     */
    public List<Topic> findTopicList(Integer page, Integer limit) throws Exception;

    /**
     * 管理员添加专题
     * @param topicPo
     * @throws Exception
     * @return TopicPo
     * @author Ren tianhe
     * @date 2019/12/13
     */
    public Integer adminAddTopic(TopicPo topicPo) throws Exception;

    /**
     * 查看专题详情
     * @param id
     * @throws Exception
     * @return TopicPo
     * @author Ren tianhe
     * @date 2019/12/13
     */
    public Topic findTopicById (Integer id) throws Exception;

    /**
     * 管理员更新专题
     * @param topicPo
     * @throws Exception
     * @return TopicPo
     * @author Ren tianhe
     * @date 2019/12/13
     */
    public Integer adminUpdateTopicById(TopicPo topicPo) throws Exception;

    /**
     * 管理员删除专题
     * @throws Exception
     * @param id
     * @return TopicPo
     * @author Ren tianhe
     * @date 2019/12/13
     */
    public Integer adminDeleteTopicById(Integer id) throws Exception ;
}
