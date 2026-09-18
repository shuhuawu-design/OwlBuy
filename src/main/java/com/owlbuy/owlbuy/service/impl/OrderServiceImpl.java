package com.owlbuy.owlbuy.service.impl;

import com.owlbuy.owlbuy.constant.PaymentMethod;
import com.owlbuy.owlbuy.dao.CartDao;
import com.owlbuy.owlbuy.dao.OrderDao;
import com.owlbuy.owlbuy.dao.ProductDao;
import com.owlbuy.owlbuy.dto.OrderRequest;
import com.owlbuy.owlbuy.model.CartItem;
import com.owlbuy.owlbuy.model.OrderItem;
import com.owlbuy.owlbuy.model.Product;
import com.owlbuy.owlbuy.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

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
        String orderSn=generateOrderSn(memberId);
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
            CartItem cartItem = cartDao.getCartItemByCartItemId(cartItemId);
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
        Integer orderId=orderDao.createOrder(orderSn,memberId,totalAmount, paymentMethod,shippingName,shippingPhone, shippingAddress);

        for(OrderItem orderItem :orderItemList){
            Integer productId=orderItem.getProductId();
            BigDecimal price=orderItem.getPrice();
            Integer quantity=orderItem.getQuantity();
            String productName=orderItem.getProductName();
            orderDao.createOrderItem(orderId, productId,productName,price,quantity);
            Integer updateRows = productDao.decreaseStock(productId,quantity);
            if (updateRows == 0){
                throw new IllegalArgumentException("商品 "+productName+ " 庫存不足，下單失敗");
            }
        }


        cartDao.deleteCartItemList(cartItemIdList);
    }

    private static String generateOrderSn(Integer memberId) {
        // 1. 年月日時分秒 (14碼)
        String dateTimeStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        // 2. 會員 ID 補齊至 6 位 (例如 memberId=123 -> "000123")
        String memberIdStr = String.format("%06d", memberId % 1000000);

        // 3. 2 位隨機數 (10 ~ 99)
        int randomNum = ThreadLocalRandom.current().nextInt(10, 100);

        return dateTimeStr + memberIdStr + randomNum;
    }
}
