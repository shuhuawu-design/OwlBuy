package com.owlbuy.owlbuy.dao.impl;

import com.owlbuy.owlbuy.dao.CartDao;
import com.owlbuy.owlbuy.dto.CartRequest;
import com.owlbuy.owlbuy.model.CartItem;
import org.springframework.stereotype.Component;

@Component
public class CartDaoImpl implements CartDao {
    @Override
    public Integer getCartByMemberId(Integer memberId) {
        return 0;
    }

    @Override
    public Integer createCart(Integer memberId) {
        return 0;
    }

    @Override
    public CartItem findCartItem(Integer cartId, Integer productId) {
        return null;
    }

    @Override
    public void createCartItem(Integer cartId, CartRequest cartRequest) {

    }

    @Override
    public void updateQuantity(Integer cartItemId, Integer newQuantity) {

    }
}
