package com.yy.mall.vo;

import java.math.BigDecimal;
import java.util.List;

public class OrderProductVo {

    private String imageHost;
    private BigDecimal productTotalPrice;

    private List<OrderItemVo> orderItemVoLists;

    public String getImageHost() {
        return imageHost;
    }

    public void setImageHost(String imageHost) {
        this.imageHost = imageHost;
    }

    public BigDecimal getProductTotalPrice() {
        return productTotalPrice;
    }

    public void setProductTotalPrice(BigDecimal productTotalPrice) {
        this.productTotalPrice = productTotalPrice;
    }

    public List<OrderItemVo> getOrderItemVoLists() {
        return orderItemVoLists;
    }

    public void setOrderItemVoLists(List<OrderItemVo> orderItemVoLists) {
        this.orderItemVoLists = orderItemVoLists;
    }
}
