package com.owlbuy.owlbuy.service.impl;

import com.owlbuy.owlbuy.dao.CartDao;
import com.owlbuy.owlbuy.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CartServiceImpl implements CartService {
    @Autowired
    private CartDao cartDao;
}
