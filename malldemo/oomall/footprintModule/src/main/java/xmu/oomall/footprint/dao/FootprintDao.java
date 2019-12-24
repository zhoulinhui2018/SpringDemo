package xmu.oomall.footprint.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import xmu.oomall.footprint.domain.FootprintItem;
import xmu.oomall.footprint.domain.FootprintItemPo;
import xmu.oomall.footprint.mapper.FootprintMapper;
import java.util.List;

/**
 * Dao层 FootprintDao
 * @author Zhang Yaqing
 * @date 2019/12/16
 */
@Repository
public class FootprintDao {
    private final FootprintMapper footprintMapper;

    public FootprintDao(FootprintMapper footprintMapper) {
        this.footprintMapper = footprintMapper;
    }
    /**
     * 用户查询足迹信息
     * @param userId 用户id
     * @return 足迹列表
     */
    public List<FootprintItem> getUserFootprintList(Integer userId) {
        return footprintMapper.getUserFootprintList(userId);
    }
    /**
     * 用户删除足迹信息
     * @param id 足迹id
     * @return 用boolean表示删除操作是否成功
     */
    public boolean deleteFootprint(Integer id) {
        return footprintMapper.deleteFootprint(id);
    }
    /**
     * 管理员按条件查询足迹信息
     * @param userId 查询的用户id
     * @param goodsId 查询的商品id
     * @return 足迹列表
     */
    public List<FootprintItem> listFootprintByCondition(Integer userId, Integer goodsId) {
        return footprintMapper.listFootprintByCondition(userId,goodsId);
    }
    /**
     * 内部接口：增加足迹
     * @param footprintItemPo 足迹项
     * @return 足迹列表
     */
    public boolean addFootprint(FootprintItemPo footprintItemPo) {
        return footprintMapper.addFootprint(footprintItemPo);
    }
}
