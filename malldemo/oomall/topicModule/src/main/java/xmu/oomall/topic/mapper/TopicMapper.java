package xmu.oomall.topic.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;
import xmu.oomall.topic.domain.TopicPo;

import java.util.List;

/**
 * 专题模块
 * @author Ren tianhe
 * @date 2019/12/13
 */
@Service
@Mapper
public interface TopicMapper {

    /**
     * 用户查看专题列表
     * @return List<TopicPo>
     * @author Ren tianhe
     * @date 2019/12/13
     */
    public List<TopicPo>  findTopicList();

    /**
     * 用户查看专题详情
     * @param id
     * @return TopicPo
     * @author Ren tianhe
     * @date 2019/12/13
     */
    public TopicPo findTopicById(Integer id);

    /**
     * 管理员添加专题
     * @param topicPo
     * @return int
     * @author Ren tianhe
     * @date 2019/12/13
     */
    public int adminAddTopic(TopicPo topicPo);

    /**
     * 管理员更新专题
     * @param topicPo
     * @return int
     * @author Ren tianhe
     * @date 2019/12/13
     */
    public int adminUpdateTopicById(TopicPo topicPo);

    /**
     * 管理员删除专题
     * @param id 所查看专题的id
     * @return int
     * @author Ren tianhe
     * @date 2019/12/13
     */
    public int adminDeleteTopicById(Integer id);
}