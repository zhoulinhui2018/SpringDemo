package xmu.oomall.footprint.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xmu.oomall.footprint.domain.FootprintItem;
import xmu.oomall.footprint.domain.FootprintItemPo;
import xmu.oomall.footprint.domain.VO.FootprintItemVo;
import xmu.oomall.footprint.service.impl.FootprintService;
import xmu.oomall.util.ResponseUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("")
@Validated
public class FootprintController {
    @Autowired
    private FootprintService footprintService;

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
        Integer userId= Integer.valueOf(request.getHeader("userid"));
        if(userId==0){
            return ResponseUtil.unlogin();
        }
        List<FootprintItem> footprintList= footprintService.getUserFootprintList(page,limit,userId);
        return ResponseUtil.ok(footprintList);
    }

    /**
     * 用户删除足迹信息
     * @param id 足迹id
     * @return 用boolean表示删除操作是否成功
     * @Author: Zhang Yaqing
     * @Date: 2019/12/12
     */
    @DeleteMapping("/footprints/{id}")
    public Object deleteFootprint(@PathVariable Integer id){
        boolean result=footprintService.deleteFootprint(id);
        if(result){
            return ResponseUtil.ok(result);
        }
        else{
            return ResponseUtil.badArgument();
        }
    }

    /**
     * 管理员按条件查询足迹信息
     * @param userName 查询的用户名
     * @param goodsName 查询的商品名
     * @param page 分页大小
     * @param limit 分页限制
     * @return 足迹列表
     * @Author: Zhang Yaqing
     * @Date: 2019/12/14
     */
    @GetMapping("/admin/footprints")
    public Object listFootprintByCondition(@RequestParam String userName,
                                           @RequestParam String goodsName,
                                           @RequestParam(defaultValue = "1") Integer page,
                                           @RequestParam(defaultValue = "10") Integer limit) {
        List<FootprintItem> footprintList=footprintService.listFootprintByCondition(page,limit,userName,goodsName);
        return ResponseUtil.ok(footprintList);
    }

    /**
     * 内部接口：增加足迹
     * @param footprintItemPo
     * @return 足迹列表
     * @Author: Zhang Yaqing
     * @Date: 2019/12/14
     */
    @PostMapping("/footprints/{userId}")
    public Object addFootprint(@RequestBody FootprintItemPo footprintItemPo){
        boolean result=footprintService.addFootprint(footprintItemPo);
        if(result){
            return ResponseUtil.ok(footprintItemPo);
        }
        else{
            return ResponseUtil.badArgument();
        }
    }

}
