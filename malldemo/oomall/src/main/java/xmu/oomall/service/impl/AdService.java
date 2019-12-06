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
    public void deleteAdbyId(Integer id){
        adDao.deleteAdbyId(id);
    }

    @Override
    public void updateAdById(Ad newAd)
    {
        adDao.updateAdById(newAd);
    }
}
