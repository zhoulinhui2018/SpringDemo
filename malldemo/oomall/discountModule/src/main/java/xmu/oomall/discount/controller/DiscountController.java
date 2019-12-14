package xmu.oomall.discount.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import xmu.oomall.discount.controller.vo.GrouponRuleVo;
import xmu.oomall.discount.dao.GroupOnDao;
import xmu.oomall.discount.domain.GrouponRulePo;
import xmu.oomall.discount.service.Impl.GroupOnRuleService;
import xmu.oomall.util.ResponseUtil;

import java.util.List;

@RestController
@RequestMapping("")
public class DiscountController {
    @Autowired
    private GroupOnRuleService groupOnRuleService;

    @Autowired
    private GroupOnDao groupOnDao;

    @Scheduled(cron = "0/3 * * * * ?")
    public Object executeAllGroupon(){

        List<GrouponRulePo> finishedGrouponRules = groupOnRuleService.findFinishedGrouponRules();
        System.out.println("test");
        return null;
    }

    /**
     * @Description: 管理员新增团购规则
     * @Param: [grouponRulePo]
     * @return: java.lang.Object
     * @Author: Zhou Linhui
     * @Date: 2019/12/11
     */
    @PostMapping("/grouponRules")
    public Object create(@RequestBody GrouponRulePo grouponRulePo){
        groupOnRuleService.add(grouponRulePo);
        return ResponseUtil.ok(grouponRulePo);
    }

    /**
     * @Description: 管理员查看某个团购规则详情
     * @Param: [id]
     * @return: java.lang.Object
     * @Author: Zhou Linhui
     * @Date: 2019/12/11
     */
    @GetMapping("/grouponRules/{id}")
    public Object detail(@PathVariable Integer id){
        GrouponRuleVo grouponRuleVo= new GrouponRuleVo();
        GrouponRulePo grouponRulePo = groupOnRuleService.findById(id);
        return ResponseUtil.ok(grouponRulePo);
    }

    /**
     * @Description: 管理员修改团购信息
     * @Param: [id, grouponRulePo]
     * @return: java.lang.Object
     * @Author: Zhou Linhui
     * @Date: 2019/12/7
     */
    @PutMapping("/grouponRules/{id}")
    public Object update(@PathVariable Integer id,@RequestBody GrouponRulePo grouponRulePo){
        grouponRulePo.setId(id);
        groupOnRuleService.update(grouponRulePo);
        return ResponseUtil.ok(grouponRulePo);
    }


    /**
     * @Description: 管理员删除团购
     * @Param: [id, grouponRulePo]
     * @return: java.lang.Object
     * @Author: Zhou Linhui
     * @Date: 2019/12/7
     */
    @DeleteMapping("/grouponRules/{id}")
    public Object delete(@PathVariable Integer id,@RequestBody GrouponRulePo grouponRulePo){
        grouponRulePo.setId(id);
        groupOnRuleService.delete(grouponRulePo);
        return ResponseUtil.ok(grouponRulePo);
    }

    /**
     * @Description: 用户开团或者入团情况
     * @Param: [id, groupOnRule]
     * @return: java.lang.Object
     * @Author: Zhou Linhui
     * @Date: 2019/12/7
     */
    @GetMapping("/goods/{id}/grouponRules")
    public Object list(@PathVariable Integer id, String goodsId,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit) {
        List<GrouponRulePo> rulesList = groupOnRuleService.searchGrouponGoods(Integer.valueOf(goodsId),page,limit);
        return ResponseUtil.ok(rulesList);
    }
}
