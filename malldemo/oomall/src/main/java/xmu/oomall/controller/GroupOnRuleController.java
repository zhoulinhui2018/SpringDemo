package xmu.oomall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xmu.oomall.controller.vo.OrderSubmitVo;
import xmu.oomall.domain.goods.GroupOnRule;
import xmu.oomall.service.impl.GroupOnRuleService;
import xmu.oomall.util.ResponseUtil;

@RestController
@RequestMapping("")
public class GroupOnRuleController {
    @Autowired
    private GroupOnRuleService groupOnRuleService;

    public Object create(@RequestBody GroupOnRule groupOnRule){
        groupOnRuleService.add(groupOnRule);
        return ResponseUtil.ok(groupOnRule);
    }
}
