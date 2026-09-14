package com.owlbuy.owlbuy.dao;

import com.owlbuy.owlbuy.dto.CartRequest;
import com.owlbuy.owlbuy.model.CartItem;

public interface CartDao {
    Integer getCartByMemberId(Integer memberId);
    Integer createCart(Integer memberId);
    CartItem findCartItem(Integer cartId,Integer productId);
    void createCartItem(Integer cartId, CartRequest cartRequest);
    void updateQuantity(Integer cartItemId,Integer newQuantity);

}
