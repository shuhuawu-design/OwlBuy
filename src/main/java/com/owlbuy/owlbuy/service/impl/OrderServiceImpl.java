package com.owlbuy.owlbuy.service.impl;

import com.owlbuy.owlbuy.dao.OrderDao;
import com.owlbuy.owlbuy.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;
}
