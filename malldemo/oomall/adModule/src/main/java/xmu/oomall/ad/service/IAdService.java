package xmu.oomall.ad.service;

import org.springframework.stereotype.Service;
import xmu.oomall.ad.domain.Ad;
import xmu.oomall.ad.domain.Log;

import java.util.List;

/**
 * Demo class IAdService
 *
 * @author Zhang Yaqing
 * @date 2019/12/20
 */
@Service
public interface IAdService {
    /**
     * 新增log
     * @param  log true
     * @author Zhou Linhui
     * @date 2019/12/18
     */
    void log(Log log);

    /**
     * 根据ID查找广告
     * @param  id id
     * @return ad
     * @author Zhou Linhui
     * @date 2019/12/4
     */
    Ad findAdById(Integer id);

    /**
     * 返回所有Ads
     * @return ad List
     * @author Zhou Linhui
     * @date 2019/12/5
     */
    List<Ad> findAllAds();

    /**
     * 删除Ad
     * @param  id id
     * @return int
     * @author Zhou Linhui
     * @date 2019/12/5
     */
    Integer deleteAdById(Integer id) throws Exception;


    /**
     * 新增广告
     * @param  ad 广告
     * @author Zhou Linhui
     * @date 2019/12/5
     */
    void addAds(Ad ad) throws Exception;

    /**
     * 更新广告
     * @param  newAd 广告
     * @return int
     * @author Zhou Linhui
     * @date 2019/12/5
     */
    Integer updateAdById(Ad newAd) throws Exception;

    /**
     * adminFindAllAds
     * @param  page 页
     * @param  limit 页
     * @param  ad ad
     * @return List<Ad>
     * @author Zhou Linhui
     * @date 2019/12/5
     */
    List<Ad> adminFindAllAds(Integer page, Integer limit,Ad ad);
    /**
     * findUserAds
     * @return List<Ad>
     * @author Zhou Linhui
     * @date 2019/12/5
     */
    List<Ad> findUserAds();
}
