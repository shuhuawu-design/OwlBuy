package com.owlbuy.owlbuy.constant;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
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
    @JsonProperty("desc")
    public String getDesc() {
        return desc;
    }

    @JsonProperty("code")
    public String getCode() {
        return this.name();
    }

    public static OrderStatus from(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }

        for (OrderStatus status : OrderStatus.values()) {
            // 比對 Enum 名稱 (例如 PENDING) 或 中文描述 (例如 待付款)
            if (status.name().equalsIgnoreCase(value) || status.getDesc().equalsIgnoreCase(value)) {
                return status;
            }
        }

        // 若完全匹配不到，回傳 null 或拋出自訂例外，避免程式直接 crash
        return null;
    }

}
