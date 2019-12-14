package xmu.oomall.discount.controller;

import com.alibaba.druid.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xmu.oomall.discount.domain.Order;
import xmu.oomall.discount.domain.Payment;
import xmu.oomall.discount.domain.Promotion.PresaleRule;
import xmu.oomall.discount.service.Impl.PresaleServiceImpl;
import xmu.oomall.util.ResponseUtil;

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

//    /**
//     * Order传给discount一个Order,计算出定金之后返回
//     * @param order
//     * @return定金
//     */
//    @GetMapping("/calcPrePay")
//
//    public Object calcDeposit(Order order) {
//        //预售订单限制只能一个规格，一条明细
//        List<OrderItem> list = order.getOrderItemList();
//        if (list.size() != 1) {
//            return ResponseUtil.fail();
//        }
//        else {
//            OrderItem item=list.get(0);
//            Integer goodsId = item.getGoodsId();
//            BigDecimal totalDeposit = BigDecimal.ZERO;
//            List<PresaleRule> ruleList = presaleService.findByGoodsId(goodsId);
//            for (PresaleRule rule : ruleList) {
//                //判断Order是不是预售订单 符不符合预售规则中的某一条
//                if (presaleService.isPresaleOrder(item, rule) == true) {
//                    totalDeposit = rule.getDeposit().multiply(BigDecimal.valueOf(item.getNumber()));
//                    return ResponseUtil.ok(totalDeposit);
//                }
//            }
//            return ResponseUtil.fail();
//        }
//    }

    @GetMapping("/prePayment")
    public Object getPrePayment(Order order,Integer maxPayTime){
        List<Payment> payments=presaleService.getPrepayment(order,maxPayTime);
        return ResponseUtil.ok(payments);
    }


}
