package xmu.oomall.discount.mapper;

import org.apache.ibatis.annotations.Mapper;
import xmu.oomall.discount.controller.vo.PresaleRuleVo;
import org.springframework.stereotype.Service;
import xmu.oomall.discount.domain.Order;
import xmu.oomall.discount.domain.Promotion.PresaleRule;

import java.math.BigDecimal;
import java.util.List;

@Service
@Mapper
public interface PresaleMapper {
    /**
     * 管理员新增预售规则
     * @param presaleRule
     */
    Integer add(PresaleRule presaleRule);

    /**
     * 管理员根据ID找到预售规则
     * @param id
     * @return
     */
    PresaleRule findPresaleRuleById(Integer id);

    /**
     * 管理员更新预售规则
     * @param presaleRule
     * @return
     */
    int updateById(PresaleRule presaleRule);

    /**
     * 管理员删除预售规则
     * @param id
     * @return
     */
    int deleteById(Integer id);

    /**
     * 根据商品ID找到所有的预售规则
     * @param goodsId
     * @return
     */
    List<PresaleRule> findByGoodsId(Integer goodsId);

    /**
     * 管理员查看预售商品列表
     * @return
     */
    List<PresaleRule> findAllPresaleRules();
}
