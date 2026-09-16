package com.owlbuy.owlbuy.constant;

public enum OrderStatus {
    PENDING("待付款"),
    PAID("已付款"),
    SHIPPED("已出貨"),
    DELIVERED("已送達"),
    COMPLETED("已完成"),
    CANCELED("已取消");

    private final String desc;

    OrderStatus(String desc) {
        this.desc = desc;
    }
    public String getDesc() {
        return desc;
    }
}
