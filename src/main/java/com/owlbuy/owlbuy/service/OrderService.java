package com.owlbuy.owlbuy.service;

import com.owlbuy.owlbuy.dto.OrderQueryParam;
import com.owlbuy.owlbuy.dto.OrderRequest;
import com.owlbuy.owlbuy.dto.OrderResponse;
import com.owlbuy.owlbuy.util.Page;

import java.util.List;

public interface OrderService {
    void createOrder(Integer memberId, OrderRequest orderRequest);
    Page<OrderResponse> getOrders(OrderQueryParam orderQueryParam);
}
