package xmu.oomall.service;

import org.springframework.stereotype.Service;
import xmu.oomall.domain.goods.GroupOnRule;

@Service
public interface IGroupOnRuleService {
    /**
    * @Description: 管理员新增团购规则
    * @Param: [groupOnRule]
    * @return: xmu.oomall.domain.goods.GroupOnRule
    * @Author: Zhou Linhui
    * @Date: 2019/12/6
    */
    public void add(GroupOnRule groupOnRule);
}
