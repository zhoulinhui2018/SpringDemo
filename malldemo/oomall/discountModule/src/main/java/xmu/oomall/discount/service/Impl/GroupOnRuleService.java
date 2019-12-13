package xmu.oomall.discount.service.Impl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmu.oomall.discount.dao.GroupOnDao;
import xmu.oomall.discount.domain.GrouponRulePo;
import xmu.oomall.discount.service.IGroupOnRuleService;

import java.util.List;

@Service
public class GroupOnRuleService implements IGroupOnRuleService {
    @Autowired
    private GroupOnDao groupOnDao;

    @Override
    public void add(GrouponRulePo grouponRulePo) {
        groupOnDao.add(grouponRulePo);
    }

    @Override
    public GrouponRulePo findById(Integer id) {
        return groupOnDao.findById(id);
    }

    @Override
    public int delete(GrouponRulePo grouponRulePo) {
        return groupOnDao.delete(grouponRulePo);
    }

    @Override
    public int update(GrouponRulePo grouponRulePo) {
        return groupOnDao.update(grouponRulePo);
    }

    @Override
    public List<GrouponRulePo> searchGrouponGoods(Integer goodsId, Integer page, Integer limit) {
        PageHelper.startPage(page,limit);
        return groupOnDao.searchGrouponGoods(goodsId);
    }
}
