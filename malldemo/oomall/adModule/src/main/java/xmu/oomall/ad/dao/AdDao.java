package xmu.oomall.ad.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import xmu.oomall.ad.domain.Ad;
import xmu.oomall.ad.mapper.AdMapper;


import java.util.List;

@Repository
public class AdDao {
    @Autowired
    private AdMapper adMapper;

    public void addAds(Ad ad){
        adMapper.addAds(ad);
    }

    public Ad findAdById(Integer id){
        Ad ad = adMapper.findAdById(id);
        return ad;
    }

    public List<Ad> findAdsDefault(){
        List<Ad> adsDefault = adMapper.findAdsDefault();
        return adsDefault;
    }

    public List<Ad> findAllAds(){
        List<Ad> allAds = adMapper.findAllAds();
        return allAds;
    }

    public Integer deleteAdbyId(Integer id){
       return adMapper.deleteAdbyId(id);
    }

    public Integer updateAdById(Ad newAd)
    {
       return adMapper.updateAdById(newAd);

    }
}
