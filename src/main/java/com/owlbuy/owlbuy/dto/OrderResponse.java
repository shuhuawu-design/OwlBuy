package com.owlbuy.owlbuy.dto;

import com.owlbuy.owlbuy.constant.OrderStatus;
import com.owlbuy.owlbuy.constant.PaymentMethod;
import com.owlbuy.owlbuy.model.OrderItem;
import com.owlbuy.owlbuy.model.Orders;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class OrderResponse {
    private Integer orderId;
    private String orderSn;
    private OrderStatus status;
    private BigDecimal totalAmount;
    private PaymentMethod paymentMethod;
    private String shippingName;
    private String shippingPhone;
    private String shippingAddress;

    private List<OrderItemResponse> orderItemList;
    private Date createdDate;
    private Date updatedDate;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

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

    public List<OrderItemResponse> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItemResponse> orderItemList) {
        this.orderItemList = orderItemList;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public OrderResponse(Orders order,List< OrderItemResponse> orderItemList) {
        this.orderId = order.getOrderId();
        this.orderSn = order.getOrderSn();
        this.status = order.getStatus();
        this.totalAmount = order.getTotalAmount();
        this.paymentMethod = order.getPaymentMethod();
        this.shippingName = order.getShippingName();
        this.shippingPhone = order.getShippingPhone();
        this.shippingAddress = order.getShippingAddress();
        this.orderItemList=orderItemList;
        this.createdDate = order.getCreatedDate();
        this.updatedDate = order.getUpdatedDate();
    }
}
