package com.owlbuy.owlbuy.controller;

import com.owlbuy.owlbuy.dto.CartItemResponse;
import com.owlbuy.owlbuy.dto.CartRequest;
import com.owlbuy.owlbuy.model.CartItem;
import com.owlbuy.owlbuy.security.CustomUserDetail;
import com.owlbuy.owlbuy.service.CartService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/cart")
    public ResponseEntity<Void>createCart(@AuthenticationPrincipal CustomUserDetail memberDetail, @Valid @RequestBody CartRequest cartRequest){
        Integer memberId=memberDetail.getMemberId();
        cartService.createCart(memberId,cartRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/cart")
    public ResponseEntity<List<CartItemResponse>>getCartItems(@AuthenticationPrincipal CustomUserDetail memberDetail){

        Integer memberId=memberDetail.getMemberId();
        List<CartItemResponse>cartItemList=cartService.getCartItems(memberId);

        return ResponseEntity.status(HttpStatus.OK).body(cartItemList);
    }

    @PutMapping("/cart/{productId}")
    public ResponseEntity<Void>decreaseCartItem(@AuthenticationPrincipal CustomUserDetail memberDetail, @PathVariable Integer productId){
        Integer memberId=memberDetail.getMemberId();
        cartService.decreaseCartItem(memberId,productId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @DeleteMapping("/cart/{productId}")
    public ResponseEntity<Void>deleteCartItem(@AuthenticationPrincipal CustomUserDetail memberDetail, @PathVariable Integer productId) {
        Integer memberId = memberDetail.getMemberId();
        cartService.deleteCartItem(memberId, productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
