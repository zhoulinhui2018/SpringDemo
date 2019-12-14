package xmu.oomall.topic.controller;

import com.alibaba.druid.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xmu.oomall.topic.domain.*;
import xmu.oomall.topic.service.impl.TopicService;
import xmu.oomall.util.ResponseUtil;

import java.util.List;


@RestController
@RequestMapping("/Topics")
public class TopicController {
    @Autowired
    private TopicService topicService;

    //如果传入的newtopic中图片url和content都为空，则认为这是个不好的请求
    private Object validate(Topic newtopic) {
        String content = newtopic.getContent();
        newtopic.setPictures();
        String picturesJson = newtopic.getPicUrlList();
        if (StringUtils.isEmpty(content)||StringUtils.isEmpty(picturesJson)) {
            return ResponseUtil.badArgument();
        }
        return null;
    }

    /**
     * 管理员用户查看所有专题
     *
     * @param
     * @Author Ren tianhe
     * @Date 2019/12/13
     */
    @GetMapping("")
    public Object findTopicList(@RequestParam(defaultValue = "1") Integer page,
                                         @RequestParam(defaultValue = "10") Integer limit
                                 ) {

        List<Topic> topicList = topicService.findTopicList(page,limit);
        return ResponseUtil.ok(topicList);
    }

    /**
     * 管理员添加专题
     *
     * @Author Ren tianhe
     * @Date 2019/12/13
     */
    @PostMapping("")
    public Object adminAddTopic(Topic newtopic){
        topicService.adminAddTopic(newtopic);
        return ResponseUtil.ok(newtopic);
    }

    /**
     * 管理员用户查看专题详情
     *
     * @param
     * @Author Ren tianhe
     * @Date 2019/12/13
     */
    @GetMapping("/{id}")
    public Object findTopicById(Integer id){
        return ResponseUtil.ok(topicService.findTopicById(id));
    }

    @PutMapping("/{id}")
    public Object adminUpdateTopicById(@PathVariable Integer id,@RequestBody Topic newtopic){
        //进行合法性判断（内容不为空）
        Object error=validate(newtopic);
        if (error != null) {
            return error;
        }
        newtopic.setId(id);
        if(topicService.adminUpdateTopicById(newtopic)==0)//更新失败，返回更新失败结果
        {
            return ResponseUtil.updatedDataFailed();
        }
        topicService.adminUpdateTopicById(newtopic);
        return ResponseUtil.ok();
    }

    @DeleteMapping("/{id}")
    public Object adminDeleteTopicById(Integer id){
        topicService.adminDeleteTopicById(id);
        return ResponseUtil.ok();
    }
}
