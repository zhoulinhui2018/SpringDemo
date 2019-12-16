package xmu.oomall.footprint.service.impl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xmu.oomall.footprint.dao.FootprintDao;
import xmu.oomall.footprint.domain.FootprintItem;
import xmu.oomall.footprint.domain.FootprintItemPo;
import xmu.oomall.footprint.service.IFootprintService;

import java.util.List;

@Transactional
@Service
public class FootprintService implements IFootprintService {
    @Autowired
    private FootprintDao footprintDao;

    @Override
    public List<FootprintItem> getUserFootprintList(Integer page, Integer limit, Integer userId) {
        PageHelper.startPage(page,limit);
        return footprintDao.getUserFootprintList(userId);
    }

    @Override
    public boolean deleteFootprint(Integer id) {
        return footprintDao.deleteFootprint(id);
    }

    @Override
    public List<FootprintItem> listFootprintByCondition(Integer page, Integer limit, String userName, String goodsName) {
        PageHelper.startPage(page,limit);
        List<FootprintItem> userFootprintList=footprintDao.listFootprintByCondition(userName,goodsName);
        return userFootprintList;
    }

    @Override
    public boolean addFootprint(FootprintItemPo footprintItemPo) {
        return footprintDao.addFootprint(footprintItemPo);
    }
}
