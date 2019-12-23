package xmu.oomall.ad.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;
import xmu.oomall.ad.domain.Ad;

import java.util.List;

/** 
* @Description:  
* @Param:  
* @return:  
* @Author: Zhou Linhui
* @Date: 2019/12/23 
*/ 
@Service
@Mapper
public interface AdMapper {
    /**
    * @Description:  fsadfdsafa
    * @Param: id
    * @return: xmu.oomall.ad.domain.Ad
    * @Author: Zhou Linhui
    * @Date: 2019/12/23
    */
    public Ad findAdById(Integer id);

    /**
    * @Description:  dadfadsfadf
    * @Param: ad
    * @return: java.util.List<xmu.oomall.ad.domain.Ad>
    * @Author: Zhou Linhui
    * @Date: 2019/12/23
    */
    public List<Ad> adminFindAllAds(Ad ad);

    /**
    * @Description:  dfdfadsfsa
    * @Param:
    * @return: java.util.List<xmu.oomall.ad.domain.Ad>
    * @Author: Zhou Linhui
    * @Date: 2019/12/23
    */
    public List<Ad> findAdsDefault();

    /**
    * @Description:  dfadfasf
    * @Param:
    * @return: java.util.List<xmu.oomall.ad.domain.Ad>
    * @Author: Zhou Linhui
    * @Date: 2019/12/23
    */
    public List<Ad> findAllAds();

    /**
    * @Description:  dfadfasdfasf
    * @Param:
    * @return: java.util.List<xmu.oomall.ad.domain.Ad>
    * @Author: Zhou Linhui
    * @Date: 2019/12/23
    */
    public List<Ad> findAllAdsAvailable();

    /**
    * @Description:  dfadfasdfaf
    * @Param: id
    * @return: java.lang.Integer
    * @Author: Zhou Linhui
    * @Date: 2019/12/23
    */
    public Integer deleteAdbyId(Integer id);

    /**
    * @Description:  dfadfasfd
    * @Param: ad
    * @return: java.lang.Integer
    * @Author: Zhou Linhui
    * @Date: 2019/12/23
    */
    public Integer addAds(Ad ad);

    /**
    * @Description: afsdgadgadga
    * @Param: newAd
    * @return: java.lang.Integer
    * @Author: Zhou Linhui
    * @Date: 2019/12/23
    */
    public Integer updateAdById(Ad newAd);

    /**
    * @Description:  dffadfadf
    * @Param: []
    * @return: java.util.List<xmu.oomall.ad.domain.Ad>
    * @Author: Zhou Linhui
    * @Date: 2019/12/23
    */
    public List<Ad> findDefaultAds();
}