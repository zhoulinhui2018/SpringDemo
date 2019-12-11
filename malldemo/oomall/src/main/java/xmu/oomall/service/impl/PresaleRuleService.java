package xmu.oomall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmu.oomall.dao.PresaleRuleDao;
import xmu.oomall.domain.goods.PresaleRule;
import xmu.oomall.service.IPresaleRuleService;

import java.util.List;

@Service
public class PresaleRuleService implements IPresaleRuleService {
    @Autowired
    private PresaleRuleDao presaleRuleDao;

    @Override
    public void add(PresaleRule presaleRule) {
        presaleRuleDao.add(presaleRule);
    }

    @Override
    public PresaleRule findById(Integer id) {
        return presaleRuleDao.findById(id);
    }

    @Override
    public void delete(PresaleRule presaleRule) {
        presaleRuleDao.delete(presaleRule);
    }

    @Override
    public void update(PresaleRule presaleRule) {
        presaleRuleDao.update(presaleRule);
    }


}
