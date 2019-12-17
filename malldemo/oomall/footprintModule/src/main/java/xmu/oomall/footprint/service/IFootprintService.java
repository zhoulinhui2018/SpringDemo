package xmu.oomall.footprint.service;

import org.springframework.stereotype.Service;
import xmu.oomall.footprint.domain.FootprintItem;
import xmu.oomall.footprint.domain.FootprintItemPo;

import java.util.List;

@Service
public interface IFootprintService {

    /**
     * 用户查询足迹信息
     * @param page 分页大小
     * @param limit 分页限制
     * @param userId 用户id
     * @return 足迹列表
     * @Author: Zhang Yaqing
     * @Date: 2019/12/14
     */
    List<FootprintItem> getUserFootprintList(Integer page, Integer limit, Integer userId);

    /**
     * 用户删除足迹信息
     * @param id 足迹id
     * @return 用boolean表示删除操作是否成功
     * @Author: Zhang Yaqing
     * @Date: 2019/12/12
     */
    boolean deleteFootprint(Integer id);

    /**
     * 管理员按条件查询足迹信息
     * @param userName 查询的用户名
     * @param goodsName 查询的商品名
     * @param page 分页大小
     * @param limit 分页限制
     * @return 足迹列表
     * @Author: Zhang Yaqing
     * @Date: 2019/12/14
     */
    List<FootprintItem> listFootprintByCondition(Integer page, Integer limit, String userName, String goodsName);

    /**
     * 内部接口：增加足迹
     * @param footprintItemPo
     * @return 足迹列表
     * @Author: Zhang Yaqing
     * @Date: 2019/12/14
     */
    boolean addFootprint(FootprintItemPo footprintItemPo);
}