package com.owlbuy.owlbuy.controller;

import com.owlbuy.owlbuy.dto.CartRequest;
import com.owlbuy.owlbuy.model.Cart;
import com.owlbuy.owlbuy.security.CustomUserDetail;
import com.owlbuy.owlbuy.service.CartService;
import com.owlbuy.owlbuy.util.Page;
import jakarta.validation.Valid;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/cart")
    public ResponseEntity<String>createCart(CustomUserDetail memberDetail, @Valid @RequestBody CartRequest cartRequest){
        Integer memberId=memberDetail.getMemberId();
        Integer cartId=cartService.createCart(memberId);
        cartService.createCartItem(cartId,cartRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("新增至購物車");
    }
}
