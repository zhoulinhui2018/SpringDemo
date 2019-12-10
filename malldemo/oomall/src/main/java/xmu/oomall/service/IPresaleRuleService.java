package xmu.oomall.service;

import org.springframework.stereotype.Service;
import xmu.oomall.domain.goods.PresaleRule;

import java.util.List;

@Service
public interface IPresaleRuleService {

    /**
     * @Description: 管理员新增预售规则
     * @Param: [presaleRule]
     * @return:
     * @Author: Zhang Yaqing
     * @Date: 2019/12/10
     */
    public void add(PresaleRule groupOnRule);

    /**
     * @Description: 管理员查看某个预售规则详情
     * @Param: [id]
     * @return: PresaleRule
     * @Author: Zhang Yaqing
     * @Date: 2019/12/10
     */
    public PresaleRule findById(Integer id);

    /**
     * @Description: 管理员修改预售规则信息
     * @Param: [id, presaleRule]
     * @return:
     * @Author: Zhang Yaqing
     * @Date: 2019/12/10
     */
    public void update(PresaleRule presaleRule);

    /**
     * @Description: 管理员删除预售规则
     * @Param: [id, presaleRule]
     * @return: java.lang.Object
     * @Author: Zhang Yaqing
     * @Date: 2019/12/10
     */
    public void delete(PresaleRule presaleRule);


}
