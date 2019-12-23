package xmu.oomall.ad.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;
import xmu.oomall.ad.domain.Ad;

import java.util.List;

/**
 * Demo class AdMapper
 *
 * @author Zhang Yaqing
 * @date 2019/12/20
 */
@Service
@Mapper
public interface AdMapper {
    /**
     * 通过id查找ad
     * @param id Ad的id
     * @return Ad
     */
    Ad findAdById(Integer id);

    /**
     * 通过对象查找ad
     * @param ad Ad
     * @return Ad list
     */
    public List<Ad> adminFindAllAds(Ad ad);
    /**
     * 通过id查找ad
     * @return Ad list
     */
    public List<Ad> findAdsDefault();
    /**
     * 某描述
     * @return Ad list
     */
    public List<Ad> findAllAds();
    /**
     * 某描述
     * @return Ad list
     */
    public List<Ad> findAllAdsAvailable();
    /**
     * 某描述
     * @param id Ad的id
     * @return Ad list
     */
    public Integer deleteAdbyId(Integer id);
    /**
     * 某描述
     * @param ad Ad
     * @return Ad list
     */
    public Integer addAds(Ad ad);
    /**
     * 某描述
     * @param newAd Ad
     * @return Ad list
     */
    public Integer updateAdById(Ad newAd);
    /**
     * 某描述
     * @return Ad list
     */
    public List<Ad> findDefaultAds();
}