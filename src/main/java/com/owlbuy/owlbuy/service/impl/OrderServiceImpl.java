package com.owlbuy.owlbuy.service.impl;

import com.owlbuy.owlbuy.constant.OrderStatus;
import com.owlbuy.owlbuy.constant.PaymentMethod;
import com.owlbuy.owlbuy.dao.CartDao;
import com.owlbuy.owlbuy.dao.OrderDao;
import com.owlbuy.owlbuy.dao.ProductDao;
import com.owlbuy.owlbuy.dto.OrderItemResponse;
import com.owlbuy.owlbuy.dto.OrderQueryParam;
import com.owlbuy.owlbuy.dto.OrderRequest;
import com.owlbuy.owlbuy.dto.OrderResponse;
import com.owlbuy.owlbuy.model.CartItem;
import com.owlbuy.owlbuy.model.OrderItem;
import com.owlbuy.owlbuy.model.Orders;
import com.owlbuy.owlbuy.model.Product;
import com.owlbuy.owlbuy.service.OrderService;
import com.owlbuy.owlbuy.util.GenerateSn;
import com.owlbuy.owlbuy.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static com.owlbuy.owlbuy.constant.PaymentMethod.COD;

@Component
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private CartDao cartDao;
    @Autowired
    private ProductDao productDao;

    @Transactional
    @Override
    public void createOrder(Integer memberId, OrderRequest orderRequest) {
        PaymentMethod paymentMethod = orderRequest.getPaymentMethod();

        String orderSn= GenerateSn.generateSn(memberId);
        String shippingName = orderRequest.getShippingName();
        String shippingPhone = orderRequest.getShippingPhone();
        String shippingAddress = orderRequest.getShippingAddress();
        List<Integer>cartItemIdList = orderRequest.getCartItemIdList();
        if(cartItemIdList==null|| cartItemIdList.isEmpty()){
            throw new IllegalArgumentException("購物車不得為空");
        }

        BigDecimal totalAmount=BigDecimal.ZERO;
        List<OrderItem>orderItemList=new ArrayList<>();

        for (Integer cartItemId : cartItemIdList) {
            CartItem cartItem = cartDao.getCartItemByCartItemId(cartItemId,memberId);
            if(cartItem==null){
                throw new IllegalArgumentException("找不到購物車項目");
            }
            Integer productId = cartItem.getProductId();

            Product product = productDao.getProductById(productId);
            if (product == null || !"ACTIVE".equals(product.getStatus())) {
                throw new IllegalArgumentException("商品不存在或已下架");
            }

            BigDecimal price = product.getPrice();
            Integer quantity = cartItem.getQuantity();
            Integer stock = product.getStock();
            if (quantity > stock) {
                throw new IllegalArgumentException("此商品庫存為:" + stock + "，欲購買數量為: " + quantity);
            }
            BigDecimal subtotal = price.multiply(new BigDecimal(quantity));
            totalAmount = totalAmount.add(subtotal);

            OrderItem orderItem= new OrderItem();
            orderItem.setProductId(productId);
            orderItem.setProductName(product.getProductName());
            orderItem.setPrice(price);
            orderItem.setQuantity(quantity);
            orderItemList.add(orderItem);


        }
        Orders order=new Orders();
        order.setOrderSn(orderSn);
        order.setMemberId(memberId);
        order.setTotalAmount(totalAmount);
        order.setPaymentMethod(paymentMethod);

        if(paymentMethod==PaymentMethod.COD) {
            order.setStatus(OrderStatus.PROCESSING);
        }else{
            order.setStatus(OrderStatus.PENDING);
        }
        order.setShippingName(shippingName);
        order.setShippingPhone(shippingPhone);
        order.setShippingAddress(shippingAddress);
        Integer orderId=orderDao.createOrder(order);



        for(OrderItem orderItem :orderItemList){
            orderItem.setOrderId(orderId);
            Integer updateRows = productDao.decreaseStock(orderItem.getProductId(), orderItem.getQuantity());
            if (updateRows == 0){
                throw new IllegalArgumentException("商品 "+orderItem.getProductName()+ " 庫存不足，下單失敗");
            }
        }

        orderDao.createOrderItem(orderItemList);
        cartDao.deleteCartItemList(cartItemIdList);
    }

    @Override
    public Page<OrderResponse> getOrders(OrderQueryParam orderQueryParam) {
        Integer total=orderDao.countOrders(orderQueryParam);

        List<Orders>orderlist=orderDao.getOrders(orderQueryParam);
        if(orderlist==null||orderlist.isEmpty()){
            Page<OrderResponse> page=new Page<>();
            page.setLimit(orderQueryParam.getLimit());
            page.setOffset(orderQueryParam.getOffset());
            page.setTotal(0);
            page.setList(new ArrayList<>());
            return page;
        }

        List<Integer>orderIdList=new ArrayList<>();
        for (Orders orders:orderlist){
            orderIdList.add(orders.getOrderId());
        }

        List<OrderItemResponse>orderItems=orderDao.getOrderItemsByOrderIdList(orderIdList);

        Map<Integer, List<OrderItemResponse>>orderItemsMap=new HashMap<>();
        for(OrderItemResponse orderItem:orderItems){
            Integer orderId=orderItem.getOrderId();

            if (!orderItemsMap.containsKey(orderId)){
                orderItemsMap.put(orderId, new ArrayList<>());
            }
            orderItemsMap.get(orderId).add(orderItem);
        }
        List<OrderResponse>orderResponseList=new ArrayList<>();
        for (Orders orders:orderlist){

            List<OrderItemResponse>orderItemList=orderItemsMap.get(orders.getOrderId());
            if(orderItemList==null||orderItemList.isEmpty()){
                orderItemList=new ArrayList<>();
            }
            OrderResponse orderResponse=new OrderResponse(orders,orderItemList);

            orderResponseList.add(orderResponse);

        }

        Page<OrderResponse> page=new Page<>();
        page.setLimit(orderQueryParam.getLimit());
        page.setOffset(orderQueryParam.getOffset());
        page.setTotal(total);
        page.setList(orderResponseList);
        return page;
    }

    @Transactional
    @Override
    public OrderResponse cancelOrder(Integer memberId, Integer orderId) {
        Orders order=orderDao.getOrderById(memberId,orderId);

        if(order==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"查詢不到該訂單");
        }

        if(order.getStatus()== OrderStatus.PENDING||order.getStatus()==OrderStatus.PROCESSING) {

            List<OrderItemResponse> orderItemResponse = orderDao.getOrderItemById(memberId, orderId);
            for(OrderItemResponse orderItem:orderItemResponse){
                productDao.increaseStock(orderItem.getProductId(), orderItem.getQuantity());
            }
            orderDao.cancelOrder(orderId);
            Orders newOrder=orderDao.getOrderById(memberId,orderId);
            return new OrderResponse(newOrder,orderItemResponse);

        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"訂單已出貨或已完成，無法取消訂單");
        }

    }
}
