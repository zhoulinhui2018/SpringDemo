package xmu.oomall.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;
import xmu.oomall.domain.Ad;

@Service
@Mapper
public interface AdMapper {
    public Ad findAdById(Integer id);
}