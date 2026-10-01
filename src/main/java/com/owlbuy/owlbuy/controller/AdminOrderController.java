package com.owlbuy.owlbuy.controller;

import com.owlbuy.owlbuy.dto.OrderUpdateRequest;
import com.owlbuy.owlbuy.model.Orders;
import com.owlbuy.owlbuy.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/admin/order")
@RestController
@PreAuthorize("hasRole('ADMIN')")
public class AdminOrderController {
    @Autowired
    private OrderService orderService;

    @PatchMapping("/{orderId}/status")
    public ResponseEntity<Orders> updateOrderStatus(@PathVariable Integer orderId,
                                                    @Valid@RequestBody OrderUpdateRequest orderUpdateRequest){
        Orders order=orderService.updateOrderStatus(orderId,orderUpdateRequest);
        return ResponseEntity.ok().body(order);
    }
}
