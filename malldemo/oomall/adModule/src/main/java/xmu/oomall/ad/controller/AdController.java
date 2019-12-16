package xmu.oomall.ad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xmu.oomall.ad.domain.Ad;
import xmu.oomall.ad.domain.Log;
import xmu.oomall.ad.service.impl.AdService;
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
    * @Description: 管理员获取广告列表
    * @Param: [page, limit]
    * @return: java.lang.Object
    * @Author: Zhou Linhui
    * @Date: 2019/12/10
    */
    @GetMapping("/admins/ads")
    public Object adminFindAdList(HttpServletRequest request,
                                  @RequestParam(defaultValue = "1") Integer page,
                                  @RequestParam(defaultValue = "10") Integer limit,
                                  @RequestParam String adTitle,
                                  @RequestParam String adContent){
        String id= request.getHeader("id");
        if (id==null){
            return ResponseUtil.unlogin();
        }
        Log log=new Log();
        log.setAdminId(Integer.valueOf(id));
        log.setIp(request.getRemoteAddr());
        log.setType(0);
        log.setStatusCode(1);
        log.setActions("查询列表");
        adService.log(log);
        Ad ad=new Ad();
        ad.setName(adTitle);
        ad.setContent(adContent);
        return adService.adminFindAllAds(page,limit,ad);
    }

    /**
    * @Description: 用户端获得广告，获取在当前时间点可以播放的广告列表，如果为0，则返回默认广告
    * @Param: []
    * @return: java.lang.Object
    * @Author: Zhou Linhui
    * @Date: 2019/12/9
    */
    @GetMapping("/ads")
    public Object userFindAdsList(){
        return ResponseUtil.ok(adService.findUserAds());
    }


    /**
    * @Description: 管理员查看单条广告
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
        Log log=new Log();
        log.setAdminId(Integer.valueOf(adminid));
        log.setIp(request.getRemoteAddr());
        log.setType(0);

        log.setActions("查询详情");
        adService.log(log);
        Ad adById = adService.findAdById(id);
        if (adById==null){
            log.setStatusCode(0);
            adService.log(log);
            return ResponseUtil.badArgumentValue();
        }
        adService.log(log);
        return  ResponseUtil.ok(adById);
    }



    /**
    * @Description: 管理员修改广告信息
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
        Log log=new Log();
        log.setAdminId(Integer.valueOf(adminid));
        log.setIp(request.getRemoteAddr());
        log.setType(0);
        log.setActions("修改");
        Object error=validate(newAd);
        if (error != null) {
            return error;
        }
        newAd.setId(id);
        if(adService.updateAdById(newAd)==0)
        {
            log.setStatusCode(0);
            adService.log(log);
            return ResponseUtil.updatedDataFailed();
        }
        adService.log(log);
        return ResponseUtil.ok(newAd);
    }


    /**
    * @Description: 管理员删除一条广告
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
        Log log=new Log();
        log.setAdminId(Integer.valueOf(adminid));
        log.setActionId(id);
        log.setIp(request.getRemoteAddr());
        log.setType(0);
        log.setActions("删除广告");
        if (id == null) {
            log.setStatusCode(0);
            adService.log(log);
            return ResponseUtil.badArgumentValue();
        }
        adService.log(log);
        adService.deleteAdbyId(id);
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
    public Object adminCreateAd(HttpServletRequest request,@RequestBody Ad ad) {
        String adminid= request.getHeader("id");
        if (adminid==null){
            return ResponseUtil.unlogin();
        }
        Log log=new Log();
        log.setAdminId(Integer.valueOf(adminid));
        log.setIp(request.getRemoteAddr());
        log.setType(0);
        log.setActions("新增");
        Object error = validate(ad);
        if (error != null) {
           return error;
        }
        if (adService.addAds(ad)!=1){
            log.setStatusCode(0);
            adService.log(log);
            return ResponseUtil.fail();
        }
        adService.log(log);
        return ResponseUtil.ok(ad);
    }

}
