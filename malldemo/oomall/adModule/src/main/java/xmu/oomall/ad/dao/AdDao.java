package xmu.oomall.ad.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import xmu.oomall.ad.domain.Ad;
import xmu.oomall.ad.mapper.AdMapper;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

@Repository
public class AdDao {
    @Autowired
    private AdMapper adMapper;
    public List<Ad> adminFindAllAds(Ad ad){
        return adMapper.adminFindAllAds(ad);
    }


    public void addAds(Ad ad){
        ad.setGmtCreate(LocalDateTime.now());
        ad.setGmtModified(LocalDateTime.now());
        ad.setBeDelete(false);
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
        newAd.setGmtModified(LocalDateTime.now());
        return adMapper.updateAdById(newAd);
    }

    public List<Ad> findUserAds(){
        List<Ad> allAds = adMapper.findAllAdsAvailable();
        LocalDateTime now = LocalDateTime.now();
        Iterator<Ad> iterator = allAds.iterator();
        while (iterator.hasNext()){
            Ad next = iterator.next();
            if (now.isAfter(next.getEndTime()) || now.isBefore(next.getStartTime())){
                iterator.remove();
            }
        }
        if (allAds.size()==0){
            List<Ad> DefaultAds=adMapper.findDefaultAds();
            return DefaultAds;
        }
        return allAds;
    }
}
