package com.owlbuy.owlbuy.service.impl;

import com.owlbuy.owlbuy.dao.CartDao;
import com.owlbuy.owlbuy.dao.ProductDao;
import com.owlbuy.owlbuy.dto.CartRequest;
import com.owlbuy.owlbuy.model.CartItem;
import com.owlbuy.owlbuy.model.Product;
import com.owlbuy.owlbuy.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CartServiceImpl implements CartService {
    @Autowired
    private CartDao cartDao;
    @Autowired
    private ProductDao productDao;

    @Override
    public Integer createCart(Integer memberId) {
        //檢查該member是否已建立購物車
        Integer cartId=cartDao.getCartByMemberId(memberId);
        if(cartId==null){
            cartId=cartDao.createCart(memberId);
        }
        return cartId;
    }

    @Transactional
    @Override
    public void createCartItem(Integer cartId, CartRequest cartRequest) {
        //檢查想加入的商品是否已下架或不存在
        Product product=productDao.getProductById(cartRequest.getProductId());
        if(product==null || "DISCONTINUED".equals(product.getStatus())){
            throw new IllegalArgumentException("商品不存在或已下架");
        }

        //檢查購物車裡是否已有相同品項
        Integer productId=product.getProductId();
        CartItem existProduct=cartDao.findCartItem(cartId,productId);

        if(existProduct==null){
            cartDao.createCartItem(cartId,cartRequest);
        }else{
            Integer cartItemId=existProduct.getCartItemId();
            Integer newQuantity=existProduct.getQuantity()+cartRequest.getQuantity();
            cartDao.updateQuantity(cartItemId,newQuantity);
        }
    }
}
