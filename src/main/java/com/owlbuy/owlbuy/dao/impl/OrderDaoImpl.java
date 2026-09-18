package com.owlbuy.owlbuy.dao.impl;

import com.owlbuy.owlbuy.constant.PaymentMethod;
import com.owlbuy.owlbuy.dao.OrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Component
public class OrderDaoImpl implements OrderDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Integer createOrder(String orderSn, Integer memberId, BigDecimal totalAmount, PaymentMethod paymentMethod, String shippingName, String shippingPhone, String shippingAddress) {
        String sql="INSERT INTO orders(order_sn, member_id, total_amount, payment_method, shipping_name, shipping_phone, shipping_address)VALUES(:order_sn, :member_id, :total_amount, :payment_method, :shipping_name, :shipping_phone, :shipping_address)";
        Map<String,Object> map=new HashMap<>();
        map.put("order_sn",orderSn);
        map.put("member_id",memberId);
        map.put("total_amount",totalAmount);
        map.put("payment_method",paymentMethod.name());
        map.put("shipping_name",shippingName);
        map.put("shipping_phone",shippingPhone);
        map.put("shipping_address",shippingAddress);

        KeyHolder keyHolder=new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql,new MapSqlParameterSource(map),keyHolder);
        Integer orderId=keyHolder.getKey().intValue();
        return orderId;
    }

    @Override
    public void createOrderItem(Integer orderId, Integer productId, String productName, BigDecimal price, Integer quantity) {
        String sql="INSERT INTO order_item(order_id,product_id,product_name,price,quantity) VALUES(:order_id, :product_id, :product_name, :price, :quantity)";
        Map<String,Object> map=new HashMap<>();
        map.put("order_id",orderId);
        map.put("product_id",productId);
        map.put("product_name",productName);
        map.put("price",price);
        map.put("quantity",quantity);

        namedParameterJdbcTemplate.update(sql,map);

    }
}
