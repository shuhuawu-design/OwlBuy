package com.owlbuy.owlbuy.dto;

import com.owlbuy.owlbuy.constant.OrderStatus;

public class OrderUpdateRequest {
    private OrderStatus status;
    private String trackingNumber;
    private String cancelReason;

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }
}
