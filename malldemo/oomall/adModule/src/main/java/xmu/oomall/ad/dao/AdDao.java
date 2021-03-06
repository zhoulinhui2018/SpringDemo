package xmu.oomall.ad.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import xmu.oomall.ad.domain.Ad;
import xmu.oomall.ad.mapper.AdMapper;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

/**
 * Demo class AdDao
 *
 * @author Zhang Yaqing
 * @date 2019/12/20
 */
@Repository
public class AdDao {
    @Autowired
    private AdMapper adMapper;
    public List<Ad> adminFindAllAds(Ad ad){
        return adMapper.adminFindAllAds(ad);
    }


    public Integer addAds(Ad ad) throws Exception{
        ad.setGmtCreate(LocalDateTime.now());
        ad.setGmtModified(LocalDateTime.now());
        ad.setBeDeleted(false);
        return adMapper.addAds(ad);
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

    public Integer deleteAdbyId(Integer id) throws Exception{
       return adMapper.deleteAdbyId(id);
    }

    public Integer updateAdById(Ad newAd) throws Exception
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
            List<Ad> defaultAds=adMapper.findDefaultAds();
            return defaultAds;
        }
        return allAds;
    }
}
