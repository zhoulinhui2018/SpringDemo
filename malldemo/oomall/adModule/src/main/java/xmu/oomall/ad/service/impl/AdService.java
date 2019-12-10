package xmu.oomall.ad.service.impl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xmu.oomall.ad.dao.AdDao;
import xmu.oomall.ad.domain.Ad;
import xmu.oomall.ad.service.IAdService;

import java.util.List;

@Transactional
@Service
public class AdService implements IAdService {
    @Autowired
    private AdDao adDao;

    @Override
    public Ad findAdById(Integer id) {
        return adDao.findAdById(id);
    }

    @Override
    public List<Ad> findAllAds() {
        List<Ad> allAds = adDao.findAllAds();
        return allAds;
    }

    @Override
    public void addAds(Ad ad) {
        adDao.addAds(ad);
    }

    @Override
    public Integer deleteAdbyId(Integer id){
        adDao.deleteAdbyId(id);
        return 1;
    }

    @Override
    public Integer updateAdById(Ad newAd)
    {
        adDao.updateAdById(newAd);
        return 1;
    }

    @Override
    public List<Ad> findUserAds() {
        return adDao.findUserAds();
    }
    @Override
    public List<Ad> adminFindAllAds(Integer page, Integer limit) {
        PageHelper.startPage(page,limit);
        return adDao.adminFindAllAds();
    }
}
