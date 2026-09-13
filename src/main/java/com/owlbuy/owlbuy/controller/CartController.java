package com.owlbuy.owlbuy.controller;

import com.owlbuy.owlbuy.model.Cart;
import com.owlbuy.owlbuy.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping
    public ResponseEntity<Cart> getCartById(@PathVariable Integer cartId) {
        return null;
    }
}
