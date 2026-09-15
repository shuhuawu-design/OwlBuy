package com.owlbuy.owlbuy.service.impl;

import com.owlbuy.owlbuy.dao.CartDao;
import com.owlbuy.owlbuy.dao.ProductDao;
import com.owlbuy.owlbuy.dto.CartItemResponse;
import com.owlbuy.owlbuy.dto.CartRequest;
import com.owlbuy.owlbuy.model.Cart;
import com.owlbuy.owlbuy.model.CartItem;
import com.owlbuy.owlbuy.model.Product;
import com.owlbuy.owlbuy.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class CartServiceImpl implements CartService {
    @Autowired
    private CartDao cartDao;
    @Autowired
    private ProductDao productDao;

    @Transactional
    @Override
    public void createCart(Integer memberId,CartRequest cartRequest) {
        Integer cartId=getValidCartId(memberId);

        //檢查想加入的商品是否已下架或不存在
        Product product=productDao.getProductById(cartRequest.getProductId());
        if(product==null || "DISCONTINUED".equals(product.getStatus())){
            throw new IllegalArgumentException("商品不存在或已下架");
        }

        //檢查購物車裡是否已有相同品項
        Integer productId=product.getProductId();
        CartItem existProduct=cartDao.findCartItem(cartId,productId);

        if(existProduct==null){
            Integer cartProductId=cartRequest.getProductId();
            Integer quantity=cartRequest.getQuantity();
            cartDao.createCartItem(cartId,cartProductId, quantity);
        }else{
            Integer cartItemId=existProduct.getCartItemId();
            Integer newQuantity=existProduct.getQuantity()+cartRequest.getQuantity();
            cartDao.updateCartQuantity(cartItemId,newQuantity);
        }
    }

    @Override
    public List<CartItemResponse> getCartItems(Integer memberId) {
        return cartDao.getCartItemsByMemberId(memberId);

    }

    @Transactional
    @Override
    public void decreaseCartItem(Integer memberId, Integer productId) {
        Integer cartId=getValidCartId(memberId);

        CartItem cartItem=cartDao.findCartItem(cartId,productId);
        if(cartItem==null){
            return;
        }
        Integer quantity=cartItem.getQuantity();

        if(quantity<=1){
            cartDao.deleteCartItem(cartId,productId);
        }else{
            Integer newQuantity=quantity-1;
            Integer cartItemId=cartItem.getCartItemId();
            cartDao.updateCartQuantity(cartItemId,newQuantity);
        }

    }

    @Transactional
    @Override
    public void deleteCartItem(Integer memberId, Integer productId) {
        Integer cartId=getValidCartId(memberId);
        cartDao.deleteCartItem(cartId,productId);
    }

    private Integer getValidCartId(Integer memberId){
        Cart cart=cartDao.getCartByMemberId(memberId);
        if(cart==null){
            return cartDao.createCart(memberId);
        }else{
            return cart.getCartId();
        }
    }

}
