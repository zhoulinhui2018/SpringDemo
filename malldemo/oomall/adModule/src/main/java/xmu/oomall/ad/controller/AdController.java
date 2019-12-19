package xmu.oomall.ad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xmu.oomall.ad.domain.Ad;
import xmu.oomall.ad.domain.Log;
import xmu.oomall.ad.service.impl.AdService;
import xmu.oomall.ad.util.FileUploadUtil;
import xmu.oomall.ad.util.IdUtil;
import xmu.oomall.ad.util.LogUtil;
import xmu.oomall.ad.util.ResponseUtil;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("")
@Validated
public class AdController {
    @Autowired
    private AdService adService;

    private Object validate(Ad newAd) {
        String name=newAd.getName();
        if(StringUtils.isEmpty(name))
        {
            return ResponseUtil.badArgument();
        }
        String content = newAd.getContent();
        if (StringUtils.isEmpty(content)) {
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
        String path = "E:/testPic/"
                + IdUtil.genImageName()
                +file.getOriginalFilename();
        String ok="Success";
        if(ok.equals(FileUploadUtil.upload(file,path))){
            String prefix="http://localhost";
            return ResponseUtil.ok(prefix+path);
        }
        return ResponseUtil.fail();
    }

    /**
    * @Description: 管理员获取广告列表 已通过
    * @Param: [page, limit]
    * @return: java.lang.Object
    * @Author: Zhou Linhui
    * @Date: 2019/12/10
    */
    @GetMapping("/admin/ads")
    public Object adminFindAdList(HttpServletRequest request,
                                  @RequestParam(defaultValue = "1") Integer page,
                                  @RequestParam(defaultValue = "10") Integer limit,
                                  @RequestParam(required = false) String adTitle,
                                  @RequestParam(required = false) String adContent){
        String id= request.getHeader("id");
        if (id==null){
            return ResponseUtil.unlogin();
        }
        Log log = LogUtil.newLog("查询广告列表", null, Integer.valueOf(id), 0, request.getRemoteAddr());
        log.setStatusCode(1);
        adService.log(log);
        Ad ad=new Ad();
        ad.setName(adTitle);
        ad.setContent(adContent);
        return ResponseUtil.ok(adService.adminFindAllAds(page,limit,ad));
    }

    /**
    * @Description: 用户端获得广告，获取在当前时间点可以播放的广告列表，如果为0，则返回默认广告 已通过
    * @Param: []
    * @return: java.lang.Object
    * @Author: Zhou Linhui
    * @Date: 2019/12/9
    */
    @GetMapping("/ads")
    public Object userFindAdsList(){
        System.out.println("test");
        return ResponseUtil.ok(adService.findUserAds());
    }


    /**
    * @Description: 管理员查看单条广告 （需要修改）
    * @Param: []
    * @return: xmu.oomall.domain.Ad
    * @Author: Zhou Linhui
    * @Date: 2019/12/5
    */
    @GetMapping("/ads/{id}")
    public Object adminFindAdById(HttpServletRequest request,@PathVariable Integer id){
        String adminid= request.getHeader("id");
        if (adminid==null){
            return ResponseUtil.unlogin();
        }
        Log log = LogUtil.newLog("搜索广告详情", id, Integer.valueOf(adminid), 0, request.getRemoteAddr());
        Ad adById = new Ad();
        try{
            adById = adService.findAdById(id);
            if(adById==null||adById.getBeDelete()){
                log.setStatusCode(0);
                adService.log(log);
                return ResponseUtil.fail(680,"获取广告失败");
            }
        }catch (Exception e){
            log.setStatusCode(0);
            adService.log(log);
            return ResponseUtil.fail(680,"获取广告失败");
        }
        log.setStatusCode(1);
        adService.log(log);
        return  ResponseUtil.ok(adById);
    }



    /**
    * @Description: 管理员修改广告信息 (时间格式问题，设置时间有问题，其他通过）
    * @Param: [newAd]
    * @return: void
    * @Author: Zhou Linhui
    * @Date: 2019/12/7
    */
    @PutMapping("/ads/{id}")
    public Object adminUpdateAd(HttpServletRequest request,@PathVariable Integer id,@RequestBody Ad newAd) {
        String adminid= request.getHeader("id");
        if (adminid==null){
            return ResponseUtil.unlogin();
        }
        Log log = LogUtil.newLog("修改广告内容", id, Integer.valueOf(adminid), 2, request.getRemoteAddr());

        Object error=validate(newAd);
        if (error != null) {
            return ResponseUtil.fail(682,"修改广告失败");
        }
        newAd.setId(id);
        try {
            Integer integer = adService.updateAdById(newAd);
            if (integer==0){
                log.setStatusCode(0);
                adService.log(log);
                return ResponseUtil.fail(682,"修改广告失败");
            }
        } catch (Exception e) {
            log.setStatusCode(0);
            adService.log(log);
            return ResponseUtil.fail(682,"修改广告失败");
        }
        adService.log(log);
        return ResponseUtil.ok(newAd);
    }


    /**
    * @Description: 管理员删除一条广告 已通过，但是状态码没确定，待确定
    * @Param: [id]
    * @return: void
    * @Author: Zhou Linhui
    * @Date: 2019/12/7
    */
    @DeleteMapping("/ads/{id}")
    public Object adminDeleteAd(HttpServletRequest request,@PathVariable Integer id){
        String adminid= request.getHeader("id");
        if (adminid==null){
            return ResponseUtil.unlogin();
        }
        Log log = LogUtil.newLog("删除广告", id, Integer.valueOf(adminid), 3, request.getRemoteAddr());
        if (id == null) {
            log.setStatusCode(0);
            adService.log(log);
            return ResponseUtil.fail(683,"删除广告失败");
        }
        try {
            Integer integer = adService.deleteAdbyId(id);
            if (integer==0){
                log.setStatusCode(0);
                adService.log(log);
                return ResponseUtil.fail(683,"删除广告失败");
            }
        } catch (Exception e) {
            log.setStatusCode(0);
            adService.log(log);
            return ResponseUtil.fail(683,"删除广告失败");
        }
        log.setStatusCode(1);
        adService.log(log);
        return ResponseUtil.ok();
    }

    /**
    * @Description: 管理员新建一条广告
    * @Param: [ad]
    * @return: java.lang.Object
    * @Author: Zhou Linhui
    * @Date: 2019/12/5
    */
    @PostMapping("/ads")
    public Object adminCreateAd(HttpServletRequest request, @RequestBody Ad ad) {
        System.out.println("test");
        String adminid= request.getHeader("id");
        if (adminid==null){
            return ResponseUtil.unlogin();
        }
        Log log=new Log();
        log.setAdminId(Integer.valueOf(adminid));
        log.setIp(request.getRemoteAddr());
        log.setType(0);
        log.setStatusCode(1);
        log.setActions("新增一条广告");
        Object error = validate(ad);
        if (error != null) {
            log.setStatusCode(0);
            adService.log(log);
            return ResponseUtil.fail(681,"创建广告失败");
        }
        System.out.println("test1");
        try {
            adService.addAds(ad);
        } catch (Exception e) {
            log.setStatusCode(0);
            adService.log(log);
            return ResponseUtil.fail(681,"创建广告失败");
        }
        System.out.println("test2");
        adService.log(log);
        return ResponseUtil.ok(ad);
    }

}
