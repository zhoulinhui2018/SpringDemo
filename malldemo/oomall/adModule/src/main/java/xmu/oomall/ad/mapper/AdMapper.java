package xmu.oomall.ad.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;
import xmu.oomall.ad.domain.Ad;

import java.util.List;

@Service
@Mapper
public interface AdMapper {

    public Ad findAdById(Integer id);

    public List<Ad> findAdsDefault();

    public List<Ad> findAllAds();

    public Integer deleteAdbyId(Integer id);

    public Integer addAds(Ad ad);

    public Integer updateAdById(Ad newAd);

}