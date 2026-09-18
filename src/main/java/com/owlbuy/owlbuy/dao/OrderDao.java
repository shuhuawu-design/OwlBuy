package com.owlbuy.owlbuy.dao;

import com.owlbuy.owlbuy.constant.PaymentMethod;

import java.math.BigDecimal;

public interface OrderDao {
    Integer createOrder(String orderSn, Integer memberId, BigDecimal totalAmount,PaymentMethod paymentMethod, String shippingName, String shippingPhone, String shippingAddress);
    void createOrderItem(Integer orderId, Integer productId, String productName, BigDecimal price, Integer quantity);
}
