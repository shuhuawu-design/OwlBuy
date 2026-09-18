package com.owlbuy.owlbuy.dao;

import com.owlbuy.owlbuy.dto.CartItemResponse;
import com.owlbuy.owlbuy.dto.CartRequest;
import com.owlbuy.owlbuy.model.Cart;
import com.owlbuy.owlbuy.model.CartItem;

import java.util.List;

public interface CartDao {
    Cart getCartByMemberId(Integer memberId);
    CartItem getCartItemByCartItemId(Integer cartItemId);
    Integer createCart(Integer memberId);
    CartItem findCartItem(Integer cartId,Integer productId);
    void createCartItem(Integer cartId, Integer productId, Integer quantity);
    void updateCartQuantity(Integer cartItemId,Integer quantity);
    void deleteCartItem(Integer cartId,Integer productId);
    void deleteCartItemList(List<Integer> cartItemIdList);
    List<CartItemResponse>getCartItemsByMemberId(Integer memberId);
}
