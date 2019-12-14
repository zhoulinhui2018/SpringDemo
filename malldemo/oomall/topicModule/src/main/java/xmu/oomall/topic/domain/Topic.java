package xmu.oomall.topic.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import com.alibaba.fastjson.JSON;

import java.time.LocalDateTime;
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
public class Topic  {

    private TopicPo topicPo;

    public TopicPo getTopicPo() {
        return topicPo;
    }

    public Topic(TopicPo topicPo, List<String> pictures) {
        this.topicPo = topicPo;
        this.pictures = pictures;
    }

    public Topic(TopicPo topicPo) {
        this.topicPo = topicPo;
    }

    public Topic() {
        this.topicPo = new TopicPo();
    }

    public Topic(Topic topic){
        this.topicPo=topic.topicPo;
    }

    //专题所包含的照片
    private List<String> pictures;

    @Override
    public String toString() {
        return "Topic{" +
                "id=" + topicPo.getId() +
                ", picUrlList=" + topicPo.getPicUrlList() +
                ", content=" + topicPo.getContent() +
                ", gmtCreate=" + topicPo.getGmtCreate() +
                ", gmtModified=" + topicPo.getGmtModified() +
                ", beDeleted=" + topicPo.getDeleted()+
                ", pictures="+pictures+
                '}';
    }


    //重写专题包含的图片的list的set和get方法

    //外部的图片url传入后存在pictures这个list中
    public void setPictures(List<String> pictures) {
        this.pictures = pictures;
        topicPo.setPicUrlList(JSON.toJSONString(this.pictures));
    }

    //通过pictures这个string<list>，来构造topicpo中的pic_url_list这个string
    public void setPictures()
    {
        topicPo.setPicUrlList(JSON.toJSONString(this.pictures));//将pictures变成json格式
    }

    //从数据库中将pic_url_list读出来后json解析
    public List<String> getPictures()
    {   String picturesJson =topicPo.getPicUrlList();
        pictures = JSON.parseArray(picturesJson,String.class);
        return pictures;
    }


    //重写set和get方法
    public String getPicUrlList() {
        return topicPo.getPicUrlList();
    }

    public void setPicUrlList(String picUrlList) {
        topicPo.setPicUrlList(picUrlList);
    }

    public Integer getId() {
        return topicPo.getId();
    }

    public void setId(Integer id) {
        topicPo.setId(id);
    }

    public String getContent() {
        return topicPo.getContent();
    }

    public void setContent(String content) {
        topicPo.setContent(content);
    }

    public LocalDateTime getGmtCreate() {
        return topicPo.getGmtCreate();
    }

    public void setGmtCreate(LocalDateTime gmtCreate) {
        topicPo.setGmtCreate(gmtCreate);
    }

    public LocalDateTime getGmtModified() {
        return topicPo.getGmtModified();
    }

    public void setGmtModified(LocalDateTime gmtModified) {
        topicPo.setGmtModified(gmtModified);
    }

    public Boolean getDeleted() {
        return topicPo.getDeleted();
    }

    public void setDeleted(Boolean deleted) {
        topicPo.setDeleted(deleted);
    }
}
