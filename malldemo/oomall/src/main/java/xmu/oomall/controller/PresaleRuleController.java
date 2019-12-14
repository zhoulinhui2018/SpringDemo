package xmu.oomall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xmu.oomall.domain.goods.PresaleRule;
import xmu.oomall.service.impl.PresaleRuleService;
import xmu.oomall.util.ResponseUtil;

import java.util.List;

@RestController
@RequestMapping("")
public class PresaleRuleController {
    @Autowired
    private PresaleRuleService presaleRuleService;

    /**
    * @Description: 管理员新增预售规则
    * @Param: [presaleRule]
    * @return: java.lang.Object
    * @Author: Zhang Yaqing
    * @Date: 2019/12/10
    */
    @PostMapping("/presaleRule")
    public Object create(@RequestBody PresaleRule presaleRule){
        presaleRuleService.add(presaleRule);
        return ResponseUtil.ok(presaleRule);
    }

    /**
    * @Description: 管理员查看某个预售规则详情
    * @Param: [id]
    * @return: java.lang.Object
    * @Author: Zhang Yaqing
    * @Date: 2019/12/10
    */
    @GetMapping("/presaleRule/{id}")
    public Object detail(@PathVariable Integer id){
        PresaleRule presaleRule = presaleRuleService.findById(id);
        return ResponseUtil.ok(presaleRule);
    }

    /** 
    * @Description: 管理员修改预售规则信息
    * @Param: [id, presaleRule]
    * @return: java.lang.Object 
    * @Author: Zhang Yaqing
    * @Date: 2019/12/10
    */ 
    @PutMapping("/presaleRule/{id}")
    public Object update(@PathVariable Integer id,@RequestBody PresaleRule presaleRule){
        presaleRuleService.update(presaleRule);
        return ResponseUtil.ok(presaleRule);
    }


    /**
    * @Description: 管理员删除预售规则
    * @Param: [id, presaleRule]
    * @return: java.lang.Object
    * @Author: Zhang Yaqing
    * @Date: 2019/12/10
    */
    @DeleteMapping("/presaleRule/{id}")
    public Object delete(@PathVariable Integer id,@RequestBody PresaleRule presaleRule){
        presaleRuleService.delete(presaleRule);
        return ResponseUtil.ok(presaleRule);
    }

    /**
     * @Description: 用户为某商品支付定金，提交订单
     * @Param: productId
     * @return: order
     * @Author: Zhang Yaqing
     * @Date: 2019/12/11
     */

}
