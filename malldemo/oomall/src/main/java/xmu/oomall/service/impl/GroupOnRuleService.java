package xmu.oomall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmu.oomall.dao.GroupOnDao;
import xmu.oomall.domain.goods.GroupOnRule;
import xmu.oomall.service.IGroupOnRuleService;

@Service
public class GroupOnRuleService implements IGroupOnRuleService {
    @Autowired
    private GroupOnDao groupOnDao;

    @Override
    public void add(GroupOnRule groupOnRule) {
        groupOnDao.add(groupOnRule);
    }

}
