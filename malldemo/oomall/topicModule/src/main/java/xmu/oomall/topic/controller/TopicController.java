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
import xmu.oomall.topic.util.*;

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
        String picturesJson = newtopic.getPicUrlList();
        if (StringUtils.isEmpty(content)) {
            return ResponseUtil.badArgument();
        }
        if(StringUtils.isEmpty(picturesJson)){
            return ResponseUtil.badArgument();
        }
        return null;
    }


    /**
     * 管理员上传话题的图片（未测试）
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
     * 用户查看所有专题（重测已通过）
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
        try{
            topics = topicService.findTopicList(page,limit);
        }catch (Exception e){
            return ResponseUtil.fail(654,"话题查看失败");
        }
        return ResponseUtil.ok(topics);
    }

    /**
     * 管理员查看所有专题 （重测已通过）
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
        log.setActions("查询话题列表");
        logService.addlog(log);
        List<Topic> topics = new ArrayList<Topic>();
        try{
            topics = topicService.findTopicList(page,limit);
        }catch (Exception e){
            log.setStatusCode(0);
            logService.addlog(log);
            return ResponseUtil.fail(654,"话题查看失败");
        }
        return ResponseUtil.ok(topics);
    }

    /**
     * 管理员添加专题（重测已通过）
     * 添加了@RequestBody后使用Json测试
     * @Author Ren tianhe
     * @Date 2019/12/13
     */
    @PostMapping("/topics")
    public Object adminAddTopic(@RequestBody TopicPo topicPo, HttpServletRequest request) {
        String adminid= request.getHeader("id");
        if (adminid==null){
            return ResponseUtil.unlogin();
        }
        //进行合法性判断（内容不为空）
        Log log=new Log();
        log.setAdminId(Integer.valueOf(adminid));
        log.setIp(request.getRemoteAddr());
        log.setType(1);
        log.setActions("添加一个话题");
        log.setStatusCode(1);
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
            return ResponseUtil.fail(602,"话题添加失败");
        }
        logService.addlog(log);
        return ResponseUtil.ok(topicPo);
    }

    /**
     * 管理员查看专题详情 (已通过)
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
        log.setActions("查看话题详情");
        log.setActionId(id);
        Topic topicById = new Topic();
        try{
            topicById = topicService.findTopicById(id);
            if (topicById==null||topicById.getDeleted()){
                log.setStatusCode(0);
                logService.addlog(log);
                return ResponseUtil.fail(650,"该话题是无效话题");
            }
        }catch (Exception e){
            log.setStatusCode(0);
            logService.addlog(log);
            return ResponseUtil.fail(654,"话题查看失败");
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
        Topic topic = new Topic();
        try{
            topic = topicService.findTopicById(id);
            if(topic==null||topic.getDeleted()){
            return ResponseUtil.fail(650,"该话题是无效话题");
        }
        }catch (Exception e){
            return ResponseUtil.fail(654,"话题查看失败");
        }
        return ResponseUtil.ok(topic);
    }

    /**
     * 管理员编辑专题（用body测是可行的）
     *
     * @param
     * @Author Ren tianhe
     * @Date 2019/12/17
     */
    @PutMapping("/topics/{id}")
    public Object adminUpdateTopicById(@PathVariable Integer id, @RequestBody TopicPo topicPo, HttpServletRequest request){
        String adminid= request.getHeader("id");
        if (adminid==null){
            return ResponseUtil.unlogin();
        }
        Log log=new Log();
        log.setAdminId(Integer.valueOf(adminid));
        log.setIp(request.getRemoteAddr());
        log.setType(0);
        log.setStatusCode(1);
        log.setActions("编辑话题");
        log.setActionId(id);
        //进行合法性判断（内容不为空）
        Object error=validate(topicPo);
        if (error != null) {
            log.setStatusCode(0);
            logService.addlog(log);
            return error;
        }
        topicPo.setId(id);
//        if(topicService.adminUpdateTopicById(topicPo)==0){
//            log.setStatusCode(0);
//            logService.addlog(log);
//            return ResponseUtil.fail(651,"话题更新失败");
//        }
        try{
            if(topicService.adminUpdateTopicById(topicPo)==0){
                log.setStatusCode(0);
                logService.addlog(log);
                return ResponseUtil.fail(651,"话题更新失败");
            }
        }catch (Exception e){
            log.setStatusCode(0);
            logService.addlog(log);
            return ResponseUtil.fail(653,"话题更新失败");
        }
        log.setStatusCode(1);
        logService.addlog(log);
        return ResponseUtil.ok();
    }

    /**
     * 管理员删除专题 （测试通过）
     *
     * @param
     * @Author Ren tianhe
     * @Date 2019/12/17
     */
    @DeleteMapping("/topics/{id}")
    public Object adminDeleteTopicById(@PathVariable Integer id,HttpServletRequest request){
        String adminid= request.getHeader("id");
        if (adminid==null){
            return ResponseUtil.unlogin();
        }
        Log log=new Log();
        log.setAdminId(Integer.valueOf(adminid));
        log.setIp(request.getRemoteAddr());
        log.setType(0);
        log.setStatusCode(1);
        log.setActions("删除话题");
        log.setActionId(id);
//        if(topicService.adminDeleteTopicById(id)==0){
//            log.setStatusCode(0);
//            logService.addlog(log);
//            return ResponseUtil.fail(653,"话题删除失败");
//        }
        try{
            if(topicService.adminDeleteTopicById(id)==0){
                log.setStatusCode(0);
                logService.addlog(log);
                return ResponseUtil.fail(653,"话题删除失败");
            }
        }catch (Exception e){
            log.setStatusCode(0);
            logService.addlog(log);
            return ResponseUtil.fail(653,"话题删除失败");
        }
        log.setStatusCode(1);
        logService.addlog(log);
        return ResponseUtil.ok();
    }
}
