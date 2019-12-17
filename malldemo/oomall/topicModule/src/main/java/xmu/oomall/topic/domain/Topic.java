package xmu.oomall.topic.domain;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @Author: 数据库与对象模型标准组
 * @Description:专题对象
 * @Data:Created in 14:50 2019/12/11
 **/
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class Topic extends TopicPo{
    public Topic() {
    }

    public Topic(List<String> pictures) {
        this.pictures = pictures;
    }

    //专题所包含的照片
    private List<String> pictures;

    public Topic(TopicPo topicPo){
        this.setId(topicPo.getId());
        this.setGmtCreate(topicPo.getGmtCreate());
        this.setGmtModified(topicPo.getGmtModified());
        this.setDeleted(topicPo.getDeleted());
        this.setPicUrlList(topicPo.getPicUrlList());
        this.setContent(topicPo.getContent());
    }

    @Override
    public String toString() {
        return "Topic{" +
                "id=" + this.getId() +
                ", picUrlList=" + this.getPicUrlList() +
                ", content=" + this.getContent() +
                ", gmtCreate=" + this.getGmtCreate() +
                ", gmtModified=" + this.getGmtModified() +
                ", beDeleted=" + this.getDeleted()+
                ", pictures="+pictures+
                '}';
    }
    //用pic_url_list字段设置
    public void setPictures() {
        String picturesJson =this.getPicUrlList();
        JSONObject jsonObject = JSON.parseObject(picturesJson);
        String picture = jsonObject.getString("pictures");
        this.pictures = JSON.parseArray(picture,String.class);
    }

    //返回pictures这个list

    //得到this.pictures
    public List<String> getPictures()
    {
        return this.pictures;
    }


}
