package xmu.oomall.discount.service.Impl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmu.oomall.discount.dao.GroupOnDao;
import xmu.oomall.discount.domain.GroupOnRule;
import xmu.oomall.discount.service.IGroupOnRuleService;

import java.util.List;

@Service
public class GroupOnRuleService implements IGroupOnRuleService {
    @Autowired
    private GroupOnDao groupOnDao;

    @Override
    public void add(GroupOnRule groupOnRule) {
        groupOnDao.add(groupOnRule);
    }

    @Override
    public GroupOnRule findById(Integer id) {
        return groupOnDao.findById(id);
    }

    @Override
    public int delete(GroupOnRule groupOnRule) {
        return groupOnDao.delete(groupOnRule);
    }

    @Override
    public int update(GroupOnRule groupOnRule) {
        return groupOnDao.update(groupOnRule);
    }

    @Override
    public List<GroupOnRule> searchGrouponGoods(Integer goodsId,Integer page,Integer limit) {
        PageHelper.startPage(page,limit);
        return groupOnDao.searchGrouponGoods(goodsId);
    }
}
