package com.owlbuy.owlbuy.service;

import com.owlbuy.owlbuy.dto.CartRequest;
import com.owlbuy.owlbuy.security.CustomUserDetail;

public interface CartService {
    Integer createCart(Integer memberId);
    void createCartItem(Integer cartId, CartRequest cartRequest);
}
