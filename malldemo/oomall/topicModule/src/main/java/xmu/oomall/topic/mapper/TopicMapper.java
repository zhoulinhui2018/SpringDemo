package xmu.oomall.topic.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;
import xmu.oomall.topic.domain.TopicPo;

import java.util.List;

@Service
@Mapper
public interface TopicMapper {

    //用户/管理员，查看专题列表
    public List<TopicPo>  findTopicList();

    // 用户/管理员，查看某条专题详情
    public TopicPo findTopicById(Integer id);

    //管理员添加专题
    public int adminAddTopic(TopicPo topicPo);

    //管理员编辑专题
    public int adminUpdateTopicById(TopicPo topicPo);

    //管理员删除专题（逻辑删除）
    public int adminDeleteTopicById(Integer id);
}