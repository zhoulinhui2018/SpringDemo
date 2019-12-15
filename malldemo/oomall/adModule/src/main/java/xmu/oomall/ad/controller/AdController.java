package xmu.oomall.ad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xmu.oomall.ad.domain.Ad;
import xmu.oomall.ad.service.impl.AdService;
import xmu.oomall.util.ResponseUtil;


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
    public Object adminFindAdList(@RequestParam(defaultValue = "1") Integer page,
                                  @RequestParam(defaultValue = "10") Integer limit,
                                  @RequestParam String adTitle,
                                  @RequestParam String adContent){

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
    public Object userFindAdsList(@RequestParam(defaultValue = "1") Integer page,
                                  @RequestParam(defaultValue = "10") Integer limit){
        return ResponseUtil.ok(adService.findUserAds(page,limit));
    }


    /**
    * @Description: 管理员查看单条广告
    * @Param: []
    * @return: xmu.oomall.domain.Ad
    * @Author: Zhou Linhui
    * @Date: 2019/12/5
    */
    @GetMapping("/ads/{id}")
    public Object adminFindAdById(@PathVariable Integer id){
        Ad adById = adService.findAdById(id);
        if (adById==null){
            return ResponseUtil.fail(690,"广告不存在");
        }
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
    public Object adminUpdateAd(@PathVariable Integer id,@RequestBody Ad newAd) {
        Object error=validate(newAd);
        if (error != null) {
            return error;
        }
        newAd.setId(id);
        if(adService.updateAdById(newAd)==0)
        {
            return ResponseUtil.fail(691,"广告更新失败");
        }
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
    public Object adminDeleteAd(@PathVariable Integer id){
        if (id == null) {
            return ResponseUtil.badArgument();
        }
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
    public Object adminCreateAd(@RequestBody Ad ad) {
      Object error = validate(ad);
        if (error != null) {
           return error;
        }
        if (adService.addAds(ad)!=1){
            return ResponseUtil.fail(691,"广告操作失败");
        }

        return ResponseUtil.ok(ad);
    }

}
