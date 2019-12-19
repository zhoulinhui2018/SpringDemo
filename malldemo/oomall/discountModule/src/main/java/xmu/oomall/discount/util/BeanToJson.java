package xmu.oomall.discount.util;

import xmu.oomall.discount.domain.Address;
import xmu.oomall.discount.domain.Order;
import xmu.oomall.discount.domain.OrderItem;
import xmu.oomall.discount.domain.User;

import java.util.ArrayList;
import java.util.List;

public class BeanToJson {
    public static void main(String[] args) {
        List<OrderItem> orderItems=new ArrayList<>();
        User user=new User();
        Order order=new Order();
        Address address=new Address();

        order.setCouponId(1);
        order.setOrderItemList(orderItems);

        String json = JacksonUtil.toJson(order);
        System.out.println(json);
    }
}
