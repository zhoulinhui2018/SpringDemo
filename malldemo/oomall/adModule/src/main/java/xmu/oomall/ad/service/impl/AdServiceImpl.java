package xmu.oomall.ad.service.impl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import xmu.oomall.ad.dao.AdDao;
import xmu.oomall.ad.domain.Ad;
import xmu.oomall.ad.domain.Log;
import xmu.oomall.ad.service.IAdService;

import java.util.List;

/**
 * Demo class AdServiceImpl
 *
 * @author Zhang Yaqing
 * @date 2019/12/20
 */
@Transactional(rollbackFor=Exception.class)
@Service
public class AdServiceImpl implements IAdService {
    @Autowired
    private AdDao adDao;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Override
    public void log(Log log) {
        RestTemplate restTemplate = new RestTemplate();
        ServiceInstance instance = loadBalancerClient.choose("logService");
        System.out.println(instance.getHost());
        System.out.println(instance.getPort());
        String reqUrl = String.format("http://%s:%s", instance.getHost(), instance.getPort() + "/log");
        restTemplate.postForObject(reqUrl,log,Log.class);
    }

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
    public void addAds(Ad ad) throws Exception{
        Integer id =adDao.addAds(ad);
    }

    @Override
    public Integer deleteAdById(Integer id) throws Exception{
        Ad ad=new Ad();
        ad.setId(id);
        adDao.updateAdById(ad);
        return adDao.deleteAdbyId(id);
    }

    @Override
    public Integer updateAdById (Ad newAd) throws Exception
    {
        return adDao.updateAdById(newAd);
    }

    @Override
    public List<Ad> findUserAds() {
        return adDao.findUserAds();
    }

    @Override
    public List<Ad> adminFindAllAds(Integer page, Integer limit,Ad ad) {
        PageHelper.startPage(page,limit);
        return adDao.adminFindAllAds(ad);
    }
}
