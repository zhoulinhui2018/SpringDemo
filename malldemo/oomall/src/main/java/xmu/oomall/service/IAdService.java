package xmu.oomall.service;

import org.springframework.stereotype.Service;
import xmu.oomall.domain.Ad;

import java.util.List;

@Service
public interface IAdService {
    /**
    * @Description: 根据ID查找广告
    * @Param: [id]
    * @return: xmu.oomall.domain.Ad
    * @Author: Zhou Linhui
    * @Date: 2019/12/4
    */
    public Ad findAdById(Integer id);

    /**
    * @Description: 返回所有Ads
    * @Param: []
    * @return: java.util.List<xmu.oomall.domain.Ad>
    * @Author: Zhou Linhui
    * @Date: 2019/12/5
    */
    public List<Ad> findAllAds();

    public void deleteAdbyId(Integer id);

    public void addAds(Ad ad);
}
