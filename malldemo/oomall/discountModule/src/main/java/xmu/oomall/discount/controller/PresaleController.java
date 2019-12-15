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
import java.util.List;

@RestController
@RequestMapping("")
public class PresaleController {
    @Autowired
    private PresaleServiceImpl presaleService;

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
    public Object update(PresaleRule presaleRule){

        if (presaleService.update(presaleRule)== 1) {
            return ResponseUtil.ok(presaleRule);
        }
        return ResponseUtil.updatedDataFailed();
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
        presaleService.delete(id);
        return ResponseUtil.ok();
    }


    /**
     * Order调discount获得预售订单的定金和尾款
     * @param order
     * @param maxPayTime
     * @return
     */

    @GetMapping("/calcWithPrePayment")
    public Object getDepositAndFinalPay(Order order,Integer maxPayTime){

        List<BigDecimal> priceList=presaleService.getDepositAndFinalPay(order,maxPayTime);
        return ResponseUtil.ok(priceList);
    }


}
