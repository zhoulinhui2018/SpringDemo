package xmu.oomall.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import xmu.oomall.domain.Ad;
import xmu.oomall.mapper.AdMapper;

import java.time.LocalDateTime;
import java.util.Iterator;
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

    public List<Ad> findUserAds(){
        List<Ad> allAds = adMapper.findAllAds();
        LocalDateTime now = LocalDateTime.now();
        Iterator<Ad> iterator = allAds.iterator();
        while (iterator.hasNext()){
            Ad next = iterator.next();
            if (now.isAfter(next.getEndTime()) || now.isBefore(next.getStartTime())){
                iterator.remove();
            }
        }
        return allAds;
    }
}
