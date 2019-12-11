package xmu.oomall.discount.controller.vo;

import xmu.oomall.discount.domain.Address;

import java.util.List;

public class OrderVo {
    /**
     *  用户在购物车中选中项目的id
     */
    private List<Integer> cartItemIds;

    /**
     * 优惠卷id
     */
    private Integer couponId;


    /****************************************************
     * 生成代码
     ****************************************************/
    @Override
    public String toString() {
        return "OrderSubmitVo{" +
                "cartItemIds=" + cartItemIds +
                ", couponId=" + couponId +
                '}';
    }

    public List<Integer> getCartItemIds() {
        return cartItemIds;
    }

    public void setCartItemIds(List<Integer> cartItemIds) {
        this.cartItemIds = cartItemIds;
    }



    public Integer getCouponId() {
        return couponId;
    }

    public void setCouponId(Integer couponId) {
        this.couponId = couponId;
    }


}
