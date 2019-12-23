package xmu.oomall.footprint.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xmu.oomall.footprint.domain.FootprintItem;
import xmu.oomall.footprint.domain.FootprintItemPo;
import xmu.oomall.footprint.domain.GoodsPo;
import xmu.oomall.footprint.domain.Log;
import xmu.oomall.footprint.service.impl.FootprintService;
import xmu.oomall.footprint.util.ResponseUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * Footprint模块
 * @author Zhang Yaqing
 * @date 2019/12/18
 */
@RestController
@RequestMapping("")
@Validated
public class FootprintController {
    @Autowired
    private FootprintService footprintService;

    private void changePoToFootprintItem(FootprintItem footprintItem){
        Integer goodsId=footprintItem.getGoodsId();
        GoodsPo goodsPo=footprintService.getGoodsPoById(goodsId);
        footprintItem.setGoodsPo(goodsPo);
    }
    /**
     * 用户查询足迹信息
     * @param request 前端请求
     * @param page 分页大小
     * @param limit 分页限制
     * @return 足迹列表
     * @Author: Zhang Yaqing
     * @Date: 2019/12/14
     */
    @GetMapping("/footprints")
    public Object getUserFootprintList(HttpServletRequest request,
                                       @RequestParam(defaultValue = "1") Integer page,
                                       @RequestParam(defaultValue = "10") Integer limit){
        String id= request.getHeader("id");
        if(id==null){
            return ResponseUtil.unlogin();
        }
        Integer userId= Integer.valueOf(id);
        List<FootprintItem> footprintList;
        try {
            footprintList= footprintService.getUserFootprintList(page,limit,userId);
        } catch (Exception e) {
            return ResponseUtil.serious();
        }
        for(int i=0;i<footprintList.size();i++){
            changePoToFootprintItem(footprintList.get(i));
        }
        return ResponseUtil.ok(footprintList);
    }


    /**
     * 管理员按条件查询足迹信息
     * @param userId 查询的用户id
     * @param goodsId 查询的商品id
     * @param page 分页大小
     * @param limit 分页限制
     * @return 足迹列表
     * @Author: Zhang Yaqing
     * @Date: 2019/12/14
     */
    @GetMapping("/admin/footprints")
    public Object listFootprintByCondition(HttpServletRequest request,
                                           @RequestParam Integer userId,
                                           @RequestParam Integer goodsId,
                                           @RequestParam(defaultValue = "1") Integer page,
                                           @RequestParam(defaultValue = "10") Integer limit) {
        String adminid= request.getHeader("id");
        if (adminid==null){
            return ResponseUtil.unlogin();
        }
        if (page<=0||limit<=0){
            return ResponseUtil.fail(580,"参数不对");
        }
        Log log=new Log();
        log.setAdminId(Integer.valueOf(adminid));
        log.setIp(request.getRemoteAddr());
        log.setType(0);
        log.setStatusCode(1);
        log.setActions("查询足迹信息");
        log.setActionId(1);

        List<FootprintItem> footprintList;
        try {
            footprintList=footprintService.listFootprintByCondition(page,limit,userId,goodsId);
        } catch (Exception e) {
            log.setStatusCode(0);
            footprintService.log(log);
            return ResponseUtil.inValidateFootprint();
        }
        footprintService.log(log);
        for(int i=0;i<footprintList.size();i++){
            changePoToFootprintItem(footprintList.get(i));
        }
        return ResponseUtil.ok(footprintList);
    }

    /*******    管理员通过姓名搜索足迹的实现      ********/
//    @GetMapping("/admin/footprints")
//    public Object listFootprintByCondition(HttpServletRequest request,
//                                           @RequestParam String userName,
//                                           @RequestParam String goodsName,
//                                           @RequestParam(defaultValue = "1") Integer page,
//                                           @RequestParam(defaultValue = "10") Integer limit) {
//        String adminid= request.getHeader("id");
//        if (adminid==null){
//            return ResponseUtil.unlogin();
//        }
//        Log log=new Log();
//        log.setAdminId(Integer.valueOf(adminid));
//        log.setIp(request.getRemoteAddr());
//        log.setType(0);
//        log.setStatusCode(1);
//        log.setActions("查询足迹信息");
//        List<FootprintItem> footprintList;
//        try {
//            footprintList=footprintService.listFootprintByCondition(page,limit,userName,goodsName);
//        } catch (Exception e) {
//            log.setStatusCode(0);
//            footprintService.log(log);
//            return ResponseUtil.inValidateFootprint();
//        }
//        footprintService.log(log);
//        return ResponseUtil.ok(footprintList);
//    }


    /**
     * 内部接口：增加足迹
     * @param footprintItemPo
     * @return 足迹列表
     * @Author: Zhang Yaqing
     * @Date: 2019/12/14
     */
    @PostMapping("/footprints")
    public Object addFootprint( FootprintItemPo footprintItemPo){
        try {
            footprintService.addFootprint(footprintItemPo);
        } catch (Exception e) {
            return ResponseUtil.addFootprintFailed();
        }
        return ResponseUtil.ok(footprintItemPo);
    }


//    /**
//     * 用户删除足迹信息（功能已删除）
//     * @param id 足迹id
//     * @return 用boolean表示删除操作是否成功
//     * @Author: Zhang Yaqing
//     * @Date: 2019/12/12
//     */
//    @DeleteMapping("/footprints/{id}")
//    public Object deleteFootprint(@PathVariable Integer id){
//        boolean result=footprintService.deleteFootprint(id);
//        if(result){
//            return ResponseUtil.ok();
//        }
//        else{
//            return ResponseUtil.inValidateFootprint();
//        }
//    }

}



