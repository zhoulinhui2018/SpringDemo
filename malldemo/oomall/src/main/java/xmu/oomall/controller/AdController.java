package xmu.oomall.controller;

import com.alibaba.druid.support.json.JSONUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xmu.oomall.domain.Ad;
import xmu.oomall.service.impl.AdService;

import java.util.List;

@Transactional(rollbackFor = Exception.class)
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

    @GetMapping("/admin/ads")
    public List<Ad> getAllAds(){
        List<Ad> allAds = adService.findAllAds();
        return allAds;
    }

    @DeleteMapping("/ads/{id}")
    public void deleteAdbyId(Integer id){
        adService.deleteAdbyId(id);
    }
}
