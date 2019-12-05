package xmu.oomall.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;
import xmu.oomall.domain.Ad;

import java.util.List;

@Service
@Mapper
public interface AdMapper {
    public Ad findAdById(Integer id);

    public List<Ad> findAdsDefault();
    public List<Ad> findAllAds();

    public void deleteAdbyId(Integer id);
}