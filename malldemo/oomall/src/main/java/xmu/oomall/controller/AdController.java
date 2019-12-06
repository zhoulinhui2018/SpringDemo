package xmu.oomall.controller;

import com.alibaba.druid.support.json.JSONUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import xmu.oomall.domain.Ad;
import xmu.oomall.service.IAdService;
import xmu.oomall.service.impl.AdService;
import xmu.oomall.util.ResponseUtil;

import java.util.List;


@RestController
@RequestMapping("")
public class AdController {
    @Autowired
    private AdService adService;

    /**
    * @Description: 用户获得按照时间排序的10条广告列表
    * @Param: []
    * @return: xmu.oomall.domain.Ad
    * @Author: Zhou Linhui
    * @Date: 2019/12/5
    */
    @GetMapping("/ads/{id}")
    public Ad getAdDetail(Integer id){
        Ad adById = adService.findAdById(id);
        return adById;
    }

    /** 
    * @Description: 管理员获得所有广告
    * @Param: [] 
    * @return: java.util.List<xmu.oomall.domain.Ad> 
    * @Author: Zhou Linhui
    * @Date: 2019/12/6 
    */ 
    @GetMapping("/admin/ads")
    public List<Ad> getAllAds(){
        List<Ad> allAds = adService.findAllAds();
        return allAds;
    }

    @PutMapping("/ads/{id}")
    public void  updateAd(Ad newAd) {
        adService.updateAdById(newAd);
    }

    @DeleteMapping("/ads/{id}")
    public void deleteAdbyId(Integer id){
        adService.deleteAdbyId(id);
    }

    /**
    * @Description: 管理员添加广告
    * @Param: [ad]
    * @return: java.lang.Object
    * @Author: Zhou Linhui
    * @Date: 2019/12/5
    */
    @PostMapping("/ads")
    public Object addAds(@RequestBody Ad ad) {
//        Object error = validate(ad);
//        if (error != null) {
//            return error;
//        }
        adService.addAds(ad);
        return ResponseUtil.ok(ad);
    }
}
