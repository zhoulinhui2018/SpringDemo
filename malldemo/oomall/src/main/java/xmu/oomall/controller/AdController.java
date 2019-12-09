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
     * @Description: 管理员查看所有的广告
     * @Param: []
     * @return: java.util.List<xmu.oomall.domain.Ad>
     * @Author: Zhou Linhui
     * @Date: 2019/12/6
     */
    @GetMapping("/admin/ads")
    public Object adminFindAdList(){
        List<Ad> allAds = adService.findAllAds();
        return allAds;
    }


    /**
    * @Description: 管理员查看单条广告
    * @Param: []
    * @return: xmu.oomall.domain.Ad
    * @Author: Zhou Linhui
    * @Date: 2019/12/5
    */
    @GetMapping("/ads/{id}")
    public Object adminFindAd(@NotNull Integer id){
        Ad adById = adService.findAdById(id);
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
    public Object adminUpdateAd(@RequestBody Ad newAd) {
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
    * @Description: 管理员删除一条广告
    * @Param: [id]
    * @return: void
    * @Author: Zhou Linhui
    * @Date: 2019/12/7
    */
    @DeleteMapping("/ads/{id}")
    public Object adminDeleteAd(@RequestBody Ad ad){
        Integer id=ad.getId();
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
    public Object adminCreateAad(@RequestBody Ad ad) {
      Object error = validate(ad);
        if (error != null) {
           return error;
       }
        adService.addAds(ad);
        return ResponseUtil.ok(ad);
    }


}
