package xmu.oomall.footprint.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;
import xmu.oomall.footprint.domain.FootprintItem;
import xmu.oomall.footprint.domain.FootprintItemPo;

import java.util.List;

/**
 * Mapper层 FootprintMapper
 * @author Zhang Yaqing
 * @date 2019/12/16
 */
@Service
@Mapper
public interface FootprintMapper {
    /**
     * 用户查询足迹信息
     * @param userId 用户id
     * @return 足迹列表
     */
    List<FootprintItem> getUserFootprintList(Integer userId);

    /**
     * 用户删除足迹信息
     * @param id 足迹id
     * @return 用boolean表示删除操作是否成功
     */
    boolean deleteFootprint(Integer id);

    /**
     * 管理员按条件查询足迹信息
     * @param userId 查询的用户id
     * @param goodsId 查询的商品id
     * @return 足迹列表
     */
    List<FootprintItem> listFootprintByCondition(Integer userId, Integer goodsId);

    /**
     * 内部接口：增加足迹
     * @param footprintItemPo 足迹项
     * @return 足迹列表
     */
    boolean addFootprint(FootprintItemPo footprintItemPo);
}