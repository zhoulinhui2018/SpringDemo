package xmu.oomall.service;

import org.springframework.stereotype.Service;
import xmu.oomall.domain.Ad;

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
}
