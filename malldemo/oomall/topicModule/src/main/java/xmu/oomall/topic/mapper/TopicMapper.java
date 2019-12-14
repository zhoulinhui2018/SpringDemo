package xmu.oomall.topic.mapper;

import net.sf.jsqlparser.statement.select.Top;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;
import xmu.oomall.topic.domain.Topic;
import xmu.oomall.topic.domain.TopicPo;

import java.util.List;

@Service
@Mapper
public interface TopicMapper {

    //从数据库中返回所有的topicpo对象
    public List<TopicPo>  findTopicList();

    // 查看某条专题详情
    public TopicPo findTopicById(Integer id);

    //管理员添加专题
    public int adminAddTopic(TopicPo newTopicPo);

    //管理员更新专题内容
    public int adminUpdateTopicById(TopicPo newTopicPo);

    //管理员删除专题（逻辑删除）
    public int adminDeleteTopicById(Integer id);
}