package xmu.oomall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xmu.oomall.dao.AdDao;
import xmu.oomall.domain.Ad;
import xmu.oomall.service.IAdService;

import java.util.List;
@Transactional
@Service
public class AdServiceImpl implements IAdService {
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
}
