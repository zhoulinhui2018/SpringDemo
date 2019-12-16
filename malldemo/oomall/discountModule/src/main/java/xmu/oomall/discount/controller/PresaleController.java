package xmu.oomall.discount.controller;

import com.alibaba.druid.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xmu.oomall.discount.domain.Order;
import xmu.oomall.discount.domain.Payment;
import xmu.oomall.discount.domain.Promotion.PresaleRule;
import xmu.oomall.discount.service.Impl.PresaleServiceImpl;
import xmu.oomall.util.ResponseUtil;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("")
public class PresaleController {
    @Autowired
    private PresaleServiceImpl presaleService;

    /**
     * 判断presaleRule是否符合规范
     * @param presaleRule
     * @return
     */
    private Object validate(PresaleRule presaleRule) {
        Integer id = presaleRule.getGoodsId();
        if (StringUtils.isEmpty(String.valueOf(id))) {
            return ResponseUtil.badArgument();
        }
        return null;
    }
    /**
     * @Description: 管理员新增预售规则
     * @Param: [presaleRule]
     * @return: java.lang.Object
     * @Author: Zhang Yaqing
     * @Date: 2019/12/10
     */
    @PostMapping("/presaleRule")
    public Object create(@RequestBody PresaleRule presaleRule){
        presaleService.add(presaleRule);
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
        PresaleRule presaleRule = presaleService.findById(id);
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
    public Object update(@PathVariable Integer id,PresaleRule presaleRule){
        Object error = validate(presaleRule);
        if (error != null) {
            return error;
        }
        presaleRule.setId(id);
        PresaleRule ruleInDB=presaleService.findById(id);
        Boolean statusCode=ruleInDB.getStatusCode();
        LocalDateTime beginTime=ruleInDB.getStartTime();
        LocalDateTime endTime=ruleInDB.getEndTime();
        LocalDateTime nowTime=LocalDateTime.now();

        //预售规则未失效
        if(statusCode==true) {
            if ((nowTime.compareTo(beginTime) >= 0) && (nowTime.compareTo(endTime) <= 0)) {
                //在预售开始到结束时间内不能改动信息
                return ResponseUtil.updatedDataFailed();
            } else {
                //预售未开始或者已经结束可以修改信息
                if (presaleService.update(presaleRule) == 0) {
                    return ResponseUtil.updatedDataFailed();
                }
                return ResponseUtil.ok(presaleRule);
            }
        }else{//预售规则已经失效
            if (presaleService.update(presaleRule) == 0) {
                return ResponseUtil.updatedDataFailed();
            }
            return ResponseUtil.ok(presaleRule);
        }
    }


    /**
     * @Description: 管理员删除预售规则
     * @Param: [id, presaleRule]
     * @return: java.lang.Object
     * @Author: Zhang Yaqing
     * @Date: 2019/12/10
     */
    @DeleteMapping("/presaleRule/{id}")
    public Object delete(@PathVariable Integer id){
        PresaleRule ruleInDB=presaleService.findById(id);
        Boolean statusCode=ruleInDB.getStatusCode();
        LocalDateTime beginTime=ruleInDB.getStartTime();
        LocalDateTime endTime=ruleInDB.getEndTime();
        LocalDateTime nowTime=LocalDateTime.now();
        //预售规则未失效
        if(statusCode==true) {
            //在预售开始到结束时间内不能删除优惠券
            if ((nowTime.compareTo(beginTime) >= 0) && (nowTime.compareTo(endTime) <= 0)) {
                return ResponseUtil.fail();
            }
            presaleService.delete(id);
            return ResponseUtil.ok();
        }else {//预售规则已失效
            presaleService.delete(id);
            return ResponseUtil.ok();
        }
    }



}
