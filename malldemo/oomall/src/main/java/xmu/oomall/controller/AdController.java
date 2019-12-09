package xmu.oomall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xmu.oomall.domain.Ad;
import xmu.oomall.service.impl.AdService;
import xmu.oomall.util.ResponseUtil;

import javax.validation.constraints.NotNull;
import java.util.List;


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

//    @GetMapping("/ads")
//    public Object userFindAd(){
//
//    }

    /**
    * @Description: 用户和管理员查看某条广告信息
    * @Param: []
    * @return: xmu.oomall.domain.Ad
    * @Author: Zhou Linhui
    * @Date: 2019/12/5
    */
    @GetMapping("/ads/{id}")
    public Object read(@NotNull Integer id){
        Ad adById = adService.findAdById(id);
        return  ResponseUtil.ok(adById);
    }

    /** 
    * @Description: 管理员获得所有广告
    * @Param: [] 
    * @return: java.util.List<xmu.oomall.domain.Ad> 
    * @Author: Zhou Linhui
    * @Date: 2019/12/6 
    */ 
    @GetMapping("/admin/ads")
    public Object list(){
        List<Ad> allAds = adService.findAllAds();
        return allAds;
    }

    /**
    * @Description: 管理员修改广告信息
    * @Param: [newAd]
    * @return: void
    * @Author: Zhou Linhui
    * @Date: 2019/12/7
    */
    @PutMapping("/ads/{id}")
    public Object update(@RequestBody Ad newAd) {
        Object error=validate(newAd);
        if (error != null) {
            return error;
        }
        if(adService.updateAdById(newAd)==0)
        {
            return ResponseUtil.updatedDataFailed();
        }
        return ResponseUtil.ok(newAd);
    }


    /**
    * @Description: 管理员删除广告
    * @Param: [id]
    * @return: void
    * @Author: Zhou Linhui
    * @Date: 2019/12/7
    */
    @DeleteMapping("/ads/{id}")
    public Object delete(@RequestBody Ad ad){
        Integer id=ad.getId();
        if (id == null) {
            return ResponseUtil.badArgument();
        }
        adService.deleteAdbyId(id);
        return ResponseUtil.ok();
    }

    /**
    * @Description: 管理员添加广告
    * @Param: [ad]
    * @return: java.lang.Object
    * @Author: Zhou Linhui
    * @Date: 2019/12/5
    */
    @PostMapping("/ads")
    public Object create(@RequestBody Ad ad) {
      Object error = validate(ad);
        if (error != null) {
           return error;
       }
        adService.addAds(ad);
        return ResponseUtil.ok(ad);
    }


}
