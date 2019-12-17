package xmu.oomall.topic.controller;

import com.alibaba.druid.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xmu.oomall.topic.domain.Log;
import xmu.oomall.topic.domain.Topic;
import xmu.oomall.topic.domain.TopicPo;
import xmu.oomall.topic.service.impl.LogService;
import xmu.oomall.topic.service.impl.TopicService;
import xmu.oomall.topic.util.FileUploadUtil;
import xmu.oomall.topic.util.IdUtil;
import xmu.oomall.topic.util.MallException;
import xmu.oomall.util.ResponseUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Topic模块
 *
 * @param
 * @Author Ren tianhe
 * @Date 2019/12/17
 */
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
    private Object validate(TopicPo newtopic) {
        String content = newtopic.getContent();
        newtopic.getPicUrlList();
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
     * 用户查看所有专题
     *
     * @param
     * @Author Ren tianhe
     * @Date 2019/12/13
     */
    @GetMapping("/topics")
    public Object userFindTopicList(@RequestParam(defaultValue = "1") Integer page,
                                    @RequestParam(defaultValue = "10") Integer limit
                               ) {
        List<Topic> topics = new ArrayList<Topic>();
        topics = topicService.findTopicList(page,limit);
        return ResponseUtil.ok(topics);
    }

    /**
     * 管理员查看所有专题
     *
     * @param
     * @Author Ren tianhe
     * @Date 2019/12/13
     */
    @GetMapping("/admin/topics")
    public Object adminFindTopicList(@RequestParam(defaultValue = "1") Integer page,
                                     @RequestParam(defaultValue = "10") Integer limit
                                 ,HttpServletRequest request) {
        String id= request.getHeader("id");
        if (id==null){
            return ResponseUtil.unlogin();
        }
        Log log=new Log();
        log.setAdminId(Integer.valueOf(id));
        log.setIp(request.getRemoteAddr());
        log.setType(0);
        log.setStatusCode(1);
        log.setActions("查询专题列表");
        logService.addlog(log);
        List<Topic> topics = new ArrayList<Topic>();
            topics = topicService.findTopicList(page,limit);
        return ResponseUtil.ok(topics);
    }

    /**
     * 管理员添加专题
     *
     * @Author Ren tianhe
     * @Date 2019/12/13
     */
    @PostMapping("/topics")
    public Object adminAddTopic(TopicPo topicPo, HttpServletRequest request) {
        String adminid= request.getHeader("id");
        if (adminid==null){
            return ResponseUtil.unlogin();
        }
        //进行合法性判断（内容不为空）
        Log log=new Log();
        log.setAdminId(Integer.valueOf(adminid));
        log.setIp(request.getRemoteAddr());
        log.setType(1);
        log.setActions("添加一个专题");
        Object error=validate(topicPo);
        if (error != null) {
            log.setStatusCode(0);
            logService.addlog(log);
            return error;
        }
        try {
            topicService.adminAddTopic(topicPo);
            log.setActionId(topicPo.getId());
        }catch (Exception e){
            log.setStatusCode(0);
            logService.addlog(log);
        }
        logService.addlog(log);
        return ResponseUtil.ok(topicPo);
    }

    /**
     * 管理员查看专题详情 已通过
     *
     * @param
     * @Author Ren tianhe
     * @Date 2019/12/13
     */
    @GetMapping("/admin/topics/{id}")
    public Object adminFindTopicById(@PathVariable Integer id, HttpServletRequest request){
        String adminid= request.getHeader("id");
        if (adminid==null){
            return ResponseUtil.unlogin();
        }
        Log log=new Log();
        log.setAdminId(Integer.valueOf(adminid));
        log.setIp(request.getRemoteAddr());
        log.setType(0);
        log.setStatusCode(1);
        log.setActions("查看专题详情");
        Topic topicById = new Topic();
        try{
            topicById = topicService.findTopicById(id);
            if (topicById==null){
                return ResponseUtil.badArgumentValue();
            }
        }catch (MallException e){
            log.setStatusCode(0);
            logService.addlog(log);
            return ResponseUtil.badArgumentValue();
        }
        log.setStatusCode(1);
        logService.addlog(log);
        return ResponseUtil.ok(topicById);
    }

    /**
     * 用户查看专题详情 已通过
     *
     * @param
     * @Author Ren tianhe
     * @Date 2019/12/13
     */
    @GetMapping("/topics/{id}")
    public Object userFindTopicById(@PathVariable Integer id){
        Topic topic=new Topic();
        try{
            topic = topicService.findTopicById(id);
        }catch (MallException e){
            return ResponseUtil.badArgumentValue();
        }
        return ResponseUtil.ok(topic);
    }

    /**
     * 管理员编辑专题
     *
     * @param
     * @Author Ren tianhe
     * @Date 2019/12/17
     */
    @PutMapping("/topics/{id}")
    public Object adminUpdateTopicById(@PathVariable Integer id,@RequestBody TopicPo topicPo, HttpServletRequest request){
        String adminid= request.getHeader("id");
        if (adminid==null){
            return ResponseUtil.unlogin();
        }
        Log log=new Log();
        log.setAdminId(Integer.valueOf(adminid));
        log.setIp(request.getRemoteAddr());
        log.setType(0);
        log.setStatusCode(1);
        log.setActions("编辑专题");
        //进行合法性判断（内容不为空）
        Object error=validate(topicPo);
        if (error != null) {
            log.setStatusCode(0);
            logService.addlog(log);
            return error;
        }
        topicPo.setId(id);
        if(topicService.adminUpdateTopicById(topicPo)==0){
            log.setStatusCode(0);
            logService.addlog(log);
            return ResponseUtil.updatedDataFailed();
        }
        log.setStatusCode(1);
        logService.addlog(log);
        return ResponseUtil.ok();
    }

    /**
     * 管理员删除专题
     *
     * @param
     * @Author Ren tianhe
     * @Date 2019/12/17
     */
    @DeleteMapping("/topics/{id}")
    public Object adminDeleteTopicById(Integer id,HttpServletRequest request){
        Integer adminid= request.getIntHeader("id");
        if (adminid==null){
            return ResponseUtil.unlogin();
        }
        Log log=new Log();
        log.setAdminId(Integer.valueOf(adminid));
        log.setIp(request.getRemoteAddr());
        log.setType(0);
        log.setStatusCode(1);
        log.setActions("删除专题");
        if(topicService.adminDeleteTopicById(id)==0){
            log.setStatusCode(0);
            logService.addlog(log);
            return ResponseUtil.updatedDataFailed();
        }
        log.setStatusCode(1);
        return ResponseUtil.ok();
    }
}
