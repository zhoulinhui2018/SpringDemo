package xmu.oomall.topic.controller;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.commons.util.IdUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xmu.oomall.topic.domain.*;
import xmu.oomall.topic.service.impl.LogService;
import xmu.oomall.topic.service.impl.TopicService;
import xmu.oomall.topic.util.FileUploadUtil;
import xmu.oomall.topic.util.IdUtil;
import xmu.oomall.topic.util.MallException;
import xmu.oomall.util.ResponseUtil;


import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("")
public class TopicController {
    @Autowired
    private TopicService topicService;

    @Autowired
    private LogService logService;

    /**
     * 如果传入的newtopic中图片url和content都为空，则认为这是个不好的请求
     */
    /**
     * 专题内容合理性判断函数
     * @Author Ren tianhe
     * @date 2019/12/15
     */
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
     * 管理员上传专题的图片
     * @param file
     * @return
     * @throws Exception
     * @Author Ren tianhe
     * @date 2019/12/15
     */
    @RequestMapping(value="/pics",method = RequestMethod.POST)
    public Object uploadPicture(@RequestParam(value = "file",required = false) MultipartFile file) throws Exception {
        if(file==null){
            return ResponseUtil.badArgument();
        }
        String path = "/var/www/tardybird/upload/"
                + IdUtil.genImageName()
                +file.getOriginalFilename();
        String ok="Success";
        if(ok.equals(FileUploadUtil.upload(file,path))){
            String prefix="http://";
            return ResponseUtil.ok(prefix+path);
        }
        return ResponseUtil.fail();
    }

    /**
     * 管理员用户查看所有专题
     *
     * @param
     * @Author Ren tianhe
     * @Date 2019/12/13
     */
    @GetMapping("/topics")
    public Object findTopicList(@RequestParam(defaultValue = "1") Integer page,
                                         @RequestParam(defaultValue = "10") Integer limit
                               ) {
        List<Topic> topics = new ArrayList<Topic>();
        try {
            topics = topicService.findTopicList(page,limit);
        }catch (MallException e){
            return ResponseUtil.badArgumentValue();
        }
        return ResponseUtil.ok(topics);
    }

    /**
     * 管理员添加专题
     *
     * @Author Ren tianhe
     * @Date 2019/12/13
     */
    @PostMapping("/topics")
    public Object adminAddTopic(Topic newtopic,HttpServletRequest request) {
        String adminid= request.getHeader("id");
        if (adminid==null){
            return ResponseUtil.unlogin();
        }
        //进行合法性判断（内容不为空）
        Object error=validate(newtopic);
        if (error != null) {
            logService.addLog(request.getIntHeader("userId"),
                    request.getHeader("ip"),1,newtopic.getId(),"管理员创建新的专题",0);
            return error;
        }
        try {
            topicService.adminAddTopic(newtopic);
        }catch (MallException e){
            logService.addLog(request.getIntHeader("userId"),
                    request.getHeader("ip"),1,newtopic.getId(),"管理员创建新的专题",0);
        }
        logService.addLog(request.getIntHeader("userId"),
                request.getHeader("ip"),1,newtopic.getId(),"管理员创建新的专题",1);
        return ResponseUtil.ok(newtopic);
    }

    /**
     * 管理员用户查看专题详情
     *
     * @param
     * @Author Ren tianhe
     * @Date 2019/12/13
     */
    @GetMapping("/topics/{id}")
    public Object findTopicById(Integer id, HttpServletRequest request){
        String adminid= request.getHeader("id");
        if (adminid==null){
            return ResponseUtil.unlogin();
        }
        try{
            topicService.findTopicById(id);
        }catch (MallException e){
            logService.addLog(request.getIntHeader("userId"),
                    request.getHeader("ip"),0,id,"查看专题详情",0);
            return e.getErrorCode();
        }
        logService.addLog(request.getIntHeader("userId"),
                request.getHeader("ip"),0,id,"查看专题详情",1);
        return ResponseUtil.ok();
    }

    //管理员修改一个专题
    @PutMapping("/topics/{id}")
    public Object adminUpdateTopicById(@PathVariable Integer id,@RequestBody Topic newtopic, HttpServletRequest request){
        String adminid= request.getHeader("id");
        if (adminid==null){
            return ResponseUtil.unlogin();
        }
        //进行合法性判断（内容不为空）
        Object error=validate(newtopic);
        if (error != null) {
            logService.addLog(request.getIntHeader("userId"),
                    request.getHeader("ip"),2,id,"管理员修改专题详情",0);
            return error;
        }
        newtopic.setId(id);
        if(topicService.adminUpdateTopicById(newtopic)==0){
            logService.addLog(request.getIntHeader("userId"),
                    request.getHeader("ip"),2,id,"管理员修改专题详情",0);
            return ResponseUtil.updatedDataFailed();
        }
        logService.addLog(request.getIntHeader("userId"),
                request.getHeader("ip"),2,id,"管理员修改专题详情",1);
        return ResponseUtil.ok();
    }

    //管理员删除一个专题
    @DeleteMapping("/topics/{id}")
    public Object adminDeleteTopicById(Integer id){
//        Integer adminid= request.getIntHeader("userId");
//        if (adminid==null){
//            return ResponseUtil.unlogin();
//        }
//        if(topicService.adminDeleteTopicById(id)==0){
//            logService.addLog(request.getIntHeader("userId"),
//                    request.getHeader("ip"),3,id,"管理员删除专题",0);
//        }
        if(topicService.adminDeleteTopicById(id)==0){
            logService.addLog(id,
                    "ip",3,id,"管理员删除专题",0);
        }
//        logService.addLog(request.getIntHeader("userId"),
//                request.getHeader("ip"),3,id,"管理员删除专题",1);
        logService.addLog(id,
                "ip",3,id,"管理员删除专题",1);
        return ResponseUtil.ok();
    }
}
