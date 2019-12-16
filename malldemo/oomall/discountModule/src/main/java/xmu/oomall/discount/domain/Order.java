package xmu.oomall.discount.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @Author: 数据库与对象模型标准组
 * @Description:订单对象
 * @Data:Created in 14:50 2019/12/11
 **/
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class Order extends OrderPo {
    private Address addressObj;
    private User user;
    private List<OrderItem> orderItemList;
    private Integer couponId;
    //这里增加了List<Payment>
    private List<Payment> paymentList;
}
