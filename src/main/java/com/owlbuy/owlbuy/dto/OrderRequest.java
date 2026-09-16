package com.owlbuy.owlbuy.dto;

import com.owlbuy.owlbuy.constant.PaymentMethod;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class OrderRequest {
    @NotNull(message = "請選擇付款方式")
    private PaymentMethod paymentMethod;
    @NotNull(message = "請填寫收件人姓名")
    private String shippingName;
    @NotNull(message = "請填寫收件人電話")
    private String shippingPhone;
    @NotNull(message = "請填寫收件人地址")
    private String shippingAddress;

    @NotEmpty(message = "請選擇至少一項商品購買")
    private List<Integer> cartItemIdList;

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getShippingName() {
        return shippingName;
    }

    public void setShippingName(String shippingName) {
        this.shippingName = shippingName;
    }

    public String getShippingPhone() {
        return shippingPhone;
    }

    public void setShippingPhone(String shippingPhone) {
        this.shippingPhone = shippingPhone;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public List<Integer> getCartItemIdList() {
        return cartItemIdList;
    }

    public void setCartItemIdList(List<Integer> cartItemIdList) {
        this.cartItemIdList = cartItemIdList;
    }
}
