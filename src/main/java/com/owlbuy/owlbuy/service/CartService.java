package com.owlbuy.owlbuy.service;

import com.owlbuy.owlbuy.dto.CartItemResponse;
import com.owlbuy.owlbuy.dto.CartRequest;

import java.util.List;

public interface CartService {
    void createCart(Integer memberId, CartRequest cartRequest);
    void decreaseCartItem(Integer memberId, Integer productId);
    void deleteCartItem(Integer memberId, Integer productId);
    List<CartItemResponse> getCartItems(Integer memberId);
}
