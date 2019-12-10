package xmu.oomall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xmu.oomall.domain.goods.GroupOnRule;
import xmu.oomall.service.impl.GroupOnRuleService;
import xmu.oomall.util.ResponseUtil;

import java.util.List;

@RestController
@RequestMapping("")
public class GroupOnRuleController {
    @Autowired
    private GroupOnRuleService groupOnRuleService;

    /**
    * @Description: 管理员新增团购规则
    * @Param: [groupOnRule]
    * @return: java.lang.Object
    * @Author: Zhou Linhui
    * @Date: 2019/12/7
    */
    @PostMapping("/grouponRules")
    public Object create(@RequestBody GroupOnRule groupOnRule){
        groupOnRuleService.add(groupOnRule);
        return ResponseUtil.ok(groupOnRule);
    }

    /**
    * @Description: 管理员查看某个团购规则详情
    * @Param: [id]
    * @return: java.lang.Object
    * @Author: Zhou Linhui
    * @Date: 2019/12/7
    */
    @GetMapping("/grouponRules/{id}")
    public Object detail(@PathVariable Integer id){
        GroupOnRule groupOnRule = groupOnRuleService.findById(id);
        return ResponseUtil.ok(groupOnRule);
    }

    /** 
    * @Description: 管理员修改团购信息 
    * @Param: [id, groupOnRule] 
    * @return: java.lang.Object 
    * @Author: Zhou Linhui
    * @Date: 2019/12/7 
    */ 
    @PutMapping("/grouponRules/{id}")
    public Object update(@PathVariable Integer id,@RequestBody GroupOnRule groupOnRule){
        groupOnRuleService.update(groupOnRule);
        return ResponseUtil.ok(groupOnRule);
    }


    /**
    * @Description: 管理员删除团购
    * @Param: [id, groupOnRule]
    * @return: java.lang.Object
    * @Author: Zhou Linhui
    * @Date: 2019/12/7
    */
    @DeleteMapping("/grouponRules/{id}")
    public Object delete(@PathVariable Integer id,@RequestBody GroupOnRule groupOnRule){
        groupOnRuleService.delete(groupOnRule);
        return ResponseUtil.ok(groupOnRule);
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
                       @RequestParam(defaultValue = "10") Integer limit,
                       @RequestParam(defaultValue = "add_time") String sort,
                       @RequestParam(defaultValue = "desc") String order) {
        List<GroupOnRule> rulesList = groupOnRuleService.searchGrouponGoods(Integer.valueOf(goodsId));
        return ResponseUtil.ok(rulesList);
    }
}
