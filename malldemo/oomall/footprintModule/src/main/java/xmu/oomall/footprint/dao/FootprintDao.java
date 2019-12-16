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
    @Autowired
    private FootprintMapper footprintMapper;

    public List<FootprintItem> getUserFootprintList(Integer userId) {
        List<FootprintItem> userFootprintList=footprintMapper.getUserFootprintList(userId);
        return userFootprintList;
    }

    public boolean deleteFootprint(Integer id) {
        return footprintMapper.deleteFootprint(id);
    }

    public List<FootprintItem> listFootprintByCondition(String userName, String goodsName) {
        List<FootprintItem> userFootprintList=footprintMapper.listFootprintByCondition(userName,goodsName);
        return userFootprintList;
    }

    public boolean addFootprint(FootprintItemPo footprintItemPo) {
        return footprintMapper.addFootprint(footprintItemPo);
    }
}
