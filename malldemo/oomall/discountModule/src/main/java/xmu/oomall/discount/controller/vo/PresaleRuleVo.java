package xmu.oomall.discount.controller.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import xmu.oomall.discount.domain.GoodsPo;
import xmu.oomall.discount.domain.Promotion.PresaleRule;

@Getter
@Setter
@ToString
public class PresaleRuleVo {

    private PresaleRule presaleRule;

    private GoodsPo goodsPo;

}
