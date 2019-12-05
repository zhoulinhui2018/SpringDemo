package xmu.oomall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmu.oomall.dao.AdDao;
import xmu.oomall.domain.Ad;
import xmu.oomall.service.IAdService;

@Service
public class AdService implements IAdService {
    @Autowired
    private AdDao adDao;

    @Override
    public Ad findAdById(Integer id) {
        return adDao.findAdById(id);
    }
}
