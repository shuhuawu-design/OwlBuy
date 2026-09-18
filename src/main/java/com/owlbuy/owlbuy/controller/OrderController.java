package com.owlbuy.owlbuy.controller;

import com.owlbuy.owlbuy.dto.OrderRequest;
import com.owlbuy.owlbuy.model.Orders;
import com.owlbuy.owlbuy.security.CustomUserDetail;
import com.owlbuy.owlbuy.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/order")
    public ResponseEntity<Void> createOrder(@AuthenticationPrincipal CustomUserDetail memberDetail, @RequestBody OrderRequest orderRequest){
        Integer memberId=memberDetail.getMemberId();
        orderService.createOrder(memberId,orderRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
