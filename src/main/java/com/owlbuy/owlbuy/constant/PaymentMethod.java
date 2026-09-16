package com.owlbuy.owlbuy.constant;

public enum PaymentMethod {
    COD("貨到付款"),
    CREDIT_CARD("信用卡"),
    TRANSFER("銀行轉帳");

    private final String description;
    PaymentMethod(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
}
