package xmu.oomall.discount.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xmu.oomall.discount.controller.vo.PresaleRuleVo;
import xmu.oomall.discount.domain.Log;
import xmu.oomall.discount.domain.Promotion.PresaleRule;
import xmu.oomall.discount.service.impl.PresaleServiceImplImpl;
import xmu.oomall.discount.util.ResponseUtil;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("")
public class PresaleController {
    @Autowired
    private PresaleServiceImplImpl presaleService;

    /**
     * @description 判断presaleRule是否符合规范
     * @param presaleRule 预售规则
     * @return boolean
     * @author Zhang Yaqing
     * @date 2019/12/10
     */
    private Boolean validate(PresaleRule presaleRule) {
       LocalDateTime startTime=presaleRule.getStartTime();
       LocalDateTime adEndTime=presaleRule.getAdEndTime();
       LocalDateTime finalStartTime=presaleRule.getFinalStartTime();
       LocalDateTime endTime=presaleRule.getEndTime();
       Boolean statusCode=presaleRule.getStatusCode();
       Integer goodsId=presaleRule.getGoodsId();
       BigDecimal deposit=presaleRule.getDeposit();
       BigDecimal finalPayment=presaleRule.getFinalPayment();
       if (startTime==null||adEndTime==null||finalStartTime==null||endTime==null) {
            return false;
       }
       if (statusCode==null||goodsId==null||deposit==null||finalPayment==null) {
           return false;
       }
       if(startTime.isAfter(adEndTime)||finalStartTime.isAfter(endTime)){
            return false;
       }
       return true;
    }

    /**
     * @description 管理员新增预售规则 （测试已通过）
     * @param presaleRule 预售规则
     * @return java.lang.Object[presaleRule]
     * @author Zhang Yaqing
     * @date 2019/12/10
     */
    @PostMapping("/presaleRules")
    public Object create(@RequestBody PresaleRule presaleRule, HttpServletRequest request) throws Exception{
        String adminid= request.getHeader("id");
        if (adminid==null){
            return ResponseUtil.unlogin();
        }
        Log log=new Log();
        log.setAdminId(Integer.valueOf(adminid));
        log.setIp(request.getRemoteAddr());
        log.setType(1);
        log.setStatusCode(1);
        log.setActions("新增预售规则");
        if (!validate(presaleRule)) {
            log.setStatusCode(0);
            presaleService.log(log);
            return ResponseUtil.invalidPresaleRule();
        }
        try{
            presaleService.add(presaleRule);
        }
        catch (Exception e){
            log.setStatusCode(0);
            presaleService.log(log);
            return ResponseUtil.addPresaleRuleFailed();
        }
        presaleService.log(log);
        return ResponseUtil.ok(presaleRule);
    }

    /**
     * @description 管理员查看某个预售规则详情 （测试已通过）
     * @param id 预售规则id
     * @return java.lang.Object[PresaleRuleVo]
     * @author Zhang Yaqing
     * @date 2019/12/10
     */
    @GetMapping("/admin/presaleRules/{id}")
    public Object detail(@PathVariable Integer id,HttpServletRequest request){
        if(id<=0){
            return ResponseUtil.fail(580,"参数不合法");
        }
        String adminid= request.getHeader("id");
        if (adminid==null){
            return ResponseUtil.unlogin();
        }
        Log log=new Log();
        log.setAdminId(Integer.valueOf(adminid));
        log.setIp(request.getRemoteAddr());
        log.setType(0);
        log.setStatusCode(1);
        log.setActionId(id);
        log.setActions("查看预售规则详情");
        PresaleRuleVo presaleRuleVo;
        try{
            presaleRuleVo = presaleService.findById(id);
        }
        catch (Exception e){
            log.setStatusCode(0);
            presaleService.log(log);
            return ResponseUtil.serious();
        }
        if(presaleRuleVo==null){
            log.setStatusCode(0);
            presaleService.log(log);
            return ResponseUtil.invalidPresaleRule();
        }
        presaleService.log(log);
        return ResponseUtil.ok(presaleRuleVo);
    }

    /**
     * @description 管理员修改预售规则信息（测试已通过）
     * @param id 预售规则id
     * @param presaleRule 新预售规则
     * @return java.lang.Object[PresaleRule]
     * @author Zhang Yaqing
     * @date 2019/12/10
     */
    @PutMapping("/presaleRules/{id}")
    public Object update(@PathVariable Integer id, @RequestBody PresaleRule presaleRule, HttpServletRequest request){
        if(id<=0){
            return ResponseUtil.fail(580,"参数不合法");
        }
        String adminid= request.getHeader("id");
        if (adminid==null){
            return ResponseUtil.unlogin();
        }
        Log log=new Log();
        log.setAdminId(Integer.valueOf(adminid));
        log.setIp(request.getRemoteAddr());
        log.setType(0);
        log.setStatusCode(1);
        log.setActionId(id);
        log.setActions("修改预售规则");
        /******* 遗留问题：如果修改后的预售规则不合法，如何回滚？目前无法保证修改后的规则中，时间一定正确 *******/
        presaleRule.setId(id);
        try{
            presaleService.update(presaleRule);
        }
        catch (Exception e){
            log.setStatusCode(0);
            presaleService.log(log);
            return ResponseUtil.updatedDataFailed();
        }
        presaleService.log(log);
        return ResponseUtil.ok(presaleRule);
    }

    /**
     * @description 管理员删除预售规则（测试已通过）
     * @param id 预售规则id
     * @return java.lang.Object
     * @author Zhang Yaqing
     * @date 2019/12/20
     */
    @DeleteMapping("/presaleRules/{id}")
    public Object delete(@PathVariable Integer id,HttpServletRequest request){
        if(id<=0){
            return ResponseUtil.fail(580,"参数不合法");
        }
        String adminid= request.getHeader("id");
        if (adminid==null){
            return ResponseUtil.unlogin();
        }
        Log log=new Log();
        log.setAdminId(Integer.valueOf(adminid));
        log.setIp(request.getRemoteAddr());
        log.setType(0);
        log.setStatusCode(1);
        log.setActionId(id);
        log.setActions("删除预售规则");

        PresaleRule presaleRule;
        //如果id不存在，抛出异常
        try{
            PresaleRuleVo presaleRuleVo=presaleService.findById(id);
            presaleRule=presaleRuleVo.getPresaleRule();
        }catch (Exception e){
            log.setStatusCode(0);
            presaleService.log(log);
            return ResponseUtil.badArgument();
        }
        LocalDateTime beginTime=presaleRule.getStartTime();
        LocalDateTime endTime=presaleRule.getEndTime();
        LocalDateTime nowTime=LocalDateTime.now();
        boolean inTime=nowTime.isAfter(beginTime)&&nowTime.isBefore(endTime);
        Boolean statusCode=presaleRule.getStatusCode();
        //如果已删除、或在预售时间内、或是未被下架，不能删除
        if(presaleRule==null||presaleRule.getBeDeleted()||inTime||statusCode){
            log.setStatusCode(0);
            presaleService.log(log);
            return ResponseUtil.deletePresaleRuleFailed();
        }
        try{
            presaleService.delete(id);
        }
        catch (Exception e){
            log.setStatusCode(0);
            presaleService.log(log);
            return ResponseUtil.deletePresaleRuleFailed();
        }
        presaleService.log(log);
        return ResponseUtil.ok();
    }

    /**
     * @description 管理员下架预售规则 （测试已通过）
     * @param id 预售规则id
     * @return java.lang.Object
     * @author Zhang Yaqing
     * @date 2019/12/20
     */
    @PostMapping("/presaleRules/{id}/invalid")
    public Object invalidate(@PathVariable Integer id, HttpServletRequest request){
        if(id<=0){
            return ResponseUtil.fail(580,"参数不合法");
        }
        String adminid= request.getHeader("id");
        if (adminid==null){
            return ResponseUtil.unlogin();
        }
        Log log=new Log();
        log.setAdminId(Integer.valueOf(adminid));
        log.setIp(request.getRemoteAddr());
        log.setType(0);
        log.setStatusCode(1);
        log.setActionId(id);
        log.setActions("修改规则详情");

        PresaleRule presaleRule;
        //如果id不存在，抛出异常
        try{
            PresaleRuleVo presaleRuleVo=presaleService.findById(id);
            presaleRule=presaleRuleVo.getPresaleRule();
        }catch (Exception e){
            log.setStatusCode(0);
            presaleService.log(log);
            return ResponseUtil.badArgument();
        }
        LocalDateTime beginTime=presaleRule.getStartTime();
        LocalDateTime endTime=presaleRule.getEndTime();
        LocalDateTime nowTime=LocalDateTime.now();
        boolean inTime=nowTime.isAfter(beginTime)&&nowTime.isBefore(endTime);
        Boolean statusCode=presaleRule.getStatusCode();
        //如果不在预售时间内，或者预售已被下架，则本次操作不能下架
        if(!inTime||!statusCode){
            log.setStatusCode(0);
            presaleService.log(log);
            return ResponseUtil.badArgument();
        }
        //先修改预售状态
        try{
            presaleService.invalidate(id);
        }
        catch (Exception e){
            log.setStatusCode(0);
            presaleService.log(log);
            return ResponseUtil.updatePresaleRuleFailed();
        }
        //再进行退款操作
        try{
            presaleService.dealRefund(presaleRule);
        }
        catch (Exception e){
            log.setStatusCode(0);
            presaleService.log(log);
            return ResponseUtil.serious();
        }
        presaleService.log(log);
        return ResponseUtil.ok(presaleRule);
    }

    /**
     * @description 管理员根据商品id查询预售规则列表 （测试已通过）
     * @param goodsId 预售规则id
     * @return java.lang.Object[PresaleRuleVoList]
     * @author Zhang Yaqing
     * @date 2019/12/10
     */
    @GetMapping("/presaleRules")
    public Object getListByGoodsId(@RequestParam Integer goodsId,
                                     @RequestParam Integer page,
                                     @RequestParam Integer limit,
                                     HttpServletRequest request){
        if(goodsId<=0||page<=0||limit<=0){
            return ResponseUtil.fail(580,"参数不合法");
        }
        String adminid= request.getHeader("id");
        if (adminid==null){
            return ResponseUtil.unlogin();
        }
        Log log=new Log();
        log.setAdminId(Integer.valueOf(adminid));
        log.setIp(request.getRemoteAddr());
        log.setType(0);
        log.setStatusCode(1);
        log.setActions("根据条件查询预售规则列表");

        List<PresaleRuleVo> presaleRuleVoList;
        try{
            presaleRuleVoList = presaleService.findPresaleRule(goodsId,page,limit);
        }
        catch (Exception e){
            log.setStatusCode(0);
            presaleService.log(log);
            return ResponseUtil.serious();
        }
        presaleService.log(log);
        return ResponseUtil.ok(presaleRuleVoList);
    }

    /**
     * @description 管理员查看预售商品列表
     * @param page 分页大小
     * @param limit 分页限制
     * @return java.lang.Object[PresaleRuleVoList]
     * @author Zhang Yaqing
     * @date 2019/12/10
     */
    @GetMapping("/admin/presaleGoods")
    public Object findAllPresaleRules(@RequestParam Integer page,@RequestParam Integer limit,
                                      HttpServletRequest request){
        if(page<=0||limit<=0){
            return ResponseUtil.fail(580,"参数不合法");
        }
        String adminid= request.getHeader("id");
        if (adminid==null){
            return ResponseUtil.unlogin();
        }
        Log log=new Log();
        log.setAdminId(Integer.valueOf(adminid));
        log.setIp(request.getRemoteAddr());
        log.setType(0);
        log.setStatusCode(1);
        log.setActions("查看预售商品列表");

        List<PresaleRuleVo> presaleRuleVoList;
        try{
            presaleRuleVoList = presaleService.findAllPresaleGoods(page, limit);
        }
        catch (Exception e){
            log.setStatusCode(0);
            presaleService.log(log);
            return ResponseUtil.serious();
        }
        presaleService.log(log);
        return ResponseUtil.ok(presaleRuleVoList);

    }

    /**
     * @description 用户根据ID查看预售规则详情（测试已通过）
     * @param id 预售规则id
     * @return java.lang.Object[PresaleRuleVo]
     * @author Zhang Yaqing
     * @date 2019/12/10
     */
    @GetMapping("/presaleRules/{id}")
    public Object getPresaleRuleById(@PathVariable Integer id){
        if(id<=0){
            return ResponseUtil.fail(580,"参数不合法");
        }
        PresaleRuleVo presaleRuleVo;
        try{
            presaleRuleVo=presaleService.findById(id);
        }
        catch (Exception e){
            return ResponseUtil.serious();
        }
        if(presaleRuleVo==null){
            return ResponseUtil.badArgument();
        }
        return ResponseUtil.ok(presaleRuleVo);
    }

//
//    /**
//     * @description 用户根据商品ID搜索预售规则（测试已通过）
//     * @param goodsId 商品id
//     * @param page 分页大小
//     * @param limit 分页限制
//     * @return java.lang.Object[PresaleRuleVoList]
//     * @author Zhang Yaqing
//     * @date 2019/12/20
//     */
//    @GetMapping("/presaleRules")
//    public Object selectPresaleRule(@RequestParam Integer goodsId,
//                                    @RequestParam Integer page,
//                                    @RequestParam Integer limit){
//        List<PresaleRuleVo> list;
//        try{
//            list= presaleService.findPresaleRule(goodsId,page,limit);
//        }
//        catch (Exception e){
//            return ResponseUtil.serious();
//        }
//        return ResponseUtil.ok(list);
//    }

    /**
     * 用户查看预售商品列表（测试已通过）
     * @param page
     * @param limit
     * @return java.lang.Object[PresaleRuleVoList]
     * @author Zhang Yaqing
     * @date 2019/12/20
     */
    @GetMapping("/presaleGoods")
    public Object getPresaleGoods(@RequestParam Integer page,@RequestParam Integer limit){
        if(page<=0||limit<=0){
            return ResponseUtil.fail(580,"参数不合法");
        }
        List<PresaleRuleVo> presaleRuleVoList;
        try{
            presaleRuleVoList = presaleService.findOnPresaleRules(page, limit);
        }
        catch (Exception e){
            return ResponseUtil.serious();
        }
        return ResponseUtil.ok(presaleRuleVoList);
    }
}
