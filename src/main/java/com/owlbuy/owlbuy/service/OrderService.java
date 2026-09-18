package com.owlbuy.owlbuy.service;

import com.owlbuy.owlbuy.dto.OrderRequest;

public interface OrderService {
    void createOrder(Integer memberId, OrderRequest orderRequest);
}
