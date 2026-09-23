package com.owlbuy.owlbuy.dao;

import com.owlbuy.owlbuy.constant.PaymentMethod;
import com.owlbuy.owlbuy.dto.OrderItemResponse;
import com.owlbuy.owlbuy.dto.OrderQueryParam;
import com.owlbuy.owlbuy.dto.OrderResponse;
import com.owlbuy.owlbuy.model.OrderItem;
import com.owlbuy.owlbuy.model.Orders;
import org.springframework.core.annotation.Order;

import java.math.BigDecimal;
import java.util.List;

public interface OrderDao {
    Integer createOrder(Orders order);
    void createOrderItem(List<OrderItem> orderItemList);
    Integer countOrders(OrderQueryParam orderQueryParam);
    List<Orders>getOrders(OrderQueryParam orderQueryParam);
    List<OrderItemResponse>getOrderItemsByOrderIdList(List<Integer> orderIdList);
    Orders getOrderById(Integer memberId, Integer orderId);
    List<OrderItemResponse> getOrderItemById(Integer memberId, Integer orderId);
    void cancelOrder(Integer orderId);



}
