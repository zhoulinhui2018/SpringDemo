package xmu.oomall.discount.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;
import xmu.oomall.discount.domain.promotion.PresaleRule;

import java.util.List;

/**
 * Demo class PresaleMapper
 *
 * @author Zhang Yaqing
 * @date 2019/12/20
 */
@Service
@Mapper
public interface PresaleMapper {
    /**
     * 管理员新增预售规则
     * @param presaleRule 1
     * @return Integer
     */
    Integer add(PresaleRule presaleRule);

    /**
     * 管理员根据ID找到预售规则
     * @param id 1
     * @return PresaleRule
     */
    PresaleRule findPresaleRuleById(Integer id);

    /**
     * 管理员更新预售规则
     * @param presaleRule 1
     * @return int
     */
    int updateById(PresaleRule presaleRule);

    /**
     * 管理员删除预售规则
     * @param id 1
     * @return 1
     */
    int deleteById(Integer id);

    /**
     * 根据商品ID找到所有的预售规则
     * @param goodsId 1
     * @return 1
     */
    List<PresaleRule> findByGoodsId(Integer goodsId);

    /**
     * 管理员查看预售商品列表
     * @return List<PresaleRule>
     */
    List<PresaleRule> findAllPresaleGoods();

    /**
     * invalidate
     * @param id 1
     * @return 1
     */
    int invalidate(Integer id);
}
