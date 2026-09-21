package com.owlbuy.owlbuy.controller;

import com.owlbuy.owlbuy.dto.OrderQueryParam;
import com.owlbuy.owlbuy.dto.OrderRequest;
import com.owlbuy.owlbuy.dto.OrderResponse;
import com.owlbuy.owlbuy.model.Orders;
import com.owlbuy.owlbuy.security.CustomUserDetail;
import com.owlbuy.owlbuy.service.OrderService;
import com.owlbuy.owlbuy.util.Page;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/order")
    public ResponseEntity<Page<OrderResponse>>getOrders(@AuthenticationPrincipal CustomUserDetail memberDetail,
                                                @RequestParam(defaultValue = "created_date") String orderBy,
                                                @RequestParam(defaultValue = "desc") String sort,
                                                @RequestParam(defaultValue = "20")@Max(50)@Min(0) Integer limit,
                                                @RequestParam(defaultValue = "0")@Min(0) Integer offset){
        OrderQueryParam orderQueryParam = new OrderQueryParam();
        orderQueryParam.setMemberId(memberDetail.getMemberId());
        orderQueryParam.setOrderBy(orderBy);
        orderQueryParam.setSort(sort);
        orderQueryParam.setOffset(offset);
        orderQueryParam.setLimit(limit);

        Page<OrderResponse> page=orderService.getOrders(orderQueryParam);

        return ResponseEntity.ok(page);
    }
}
