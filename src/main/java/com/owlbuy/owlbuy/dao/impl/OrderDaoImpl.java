package com.owlbuy.owlbuy.dao.impl;

import com.owlbuy.owlbuy.constant.PaymentMethod;
import com.owlbuy.owlbuy.dao.OrderDao;
import com.owlbuy.owlbuy.dto.OrderItemResponse;
import com.owlbuy.owlbuy.dto.OrderQueryParam;
import com.owlbuy.owlbuy.dto.OrderResponse;
import com.owlbuy.owlbuy.model.OrderItem;
import com.owlbuy.owlbuy.model.Orders;
import com.owlbuy.owlbuy.rowmapper.OrderItemResponseRowMapper;
import com.owlbuy.owlbuy.rowmapper.OrderRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OrderDaoImpl implements OrderDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Integer createOrder(Orders order) {
        String sql="INSERT INTO orders(order_sn, member_id, total_amount, payment_method, shipping_name, shipping_phone, shipping_address)VALUES(:order_sn, :member_id, :total_amount, :payment_method, :shipping_name, :shipping_phone, :shipping_address)";
        Map<String,Object> map=new HashMap<>();
        map.put("order_sn",order.getOrderSn());
        map.put("member_id",order.getMemberId());
        map.put("total_amount",order.getTotalAmount());
        map.put("payment_method",order.getPaymentMethod().name());
        map.put("shipping_name",order.getShippingName());
        map.put("shipping_phone",order.getShippingPhone());
        map.put("shipping_address",order.getShippingAddress());

        KeyHolder keyHolder=new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql,new MapSqlParameterSource(map),keyHolder);
        Integer orderId=keyHolder.getKey().intValue();
        return orderId;
    }

    @Override
    public void createOrderItem(List<OrderItem> orderItemList) {
        String sql="INSERT INTO order_item(order_id,product_id,product_name,price,quantity) VALUES(:order_id, :product_id, :product_name, :price, :quantity)";

        MapSqlParameterSource[] params=new MapSqlParameterSource[orderItemList.size()];
        for(int i=0;i<orderItemList.size();i++){
            OrderItem orderItem=orderItemList.get(i);
            params[i]=new MapSqlParameterSource();
            params[i].addValue("order_id",orderItem.getOrderId());
            params[i].addValue("product_id",orderItem.getProductId());
            params[i].addValue("product_name",orderItem.getProductName());
            params[i].addValue("price",orderItem.getPrice());
            params[i].addValue("quantity",orderItem.getQuantity());
        }

        namedParameterJdbcTemplate.batchUpdate(sql,params);

    }

    @Override
    public Integer countOrders(OrderQueryParam orderQueryParam) {
        String sql="SELECT count(*) FROM orders WHERE member_id= :member_id";
        Map<String,Object> map=new HashMap<>();
        map.put("member_id",orderQueryParam.getMemberId());

        Integer countOrders=namedParameterJdbcTemplate.queryForObject(sql,map,Integer.class);
        return countOrders;
    }

    @Override
    public List<Orders> getOrders(OrderQueryParam orderQueryParam) {
        String sql= """
                SELECT order_id, order_sn, member_id, status, total_amount, payment_method, shipping_name, shipping_phone,
                shipping_address, created_date, updated_date
                FROM orders WHERE 1=1 """;
        Map<String,Object> map=new HashMap<>();

        if(orderQueryParam.getMemberId()!=null){
            sql=sql+" AND member_id=:member_id ";
            map.put("member_id",orderQueryParam.getMemberId());
        }

        if (orderQueryParam.getOrderBy() != null && !orderQueryParam.getOrderBy().isBlank()) {
            String sort = (orderQueryParam.getSort() != null && !orderQueryParam.getSort().isBlank())
                    ? orderQueryParam.getSort() : "DESC";
            sql=sql+" ORDER BY "+orderQueryParam.getOrderBy()+" "+sort+ " ";
        }else{
            sql=sql+" ORDER BY updated_date DESC ";
        }

        sql=sql+" LIMIT :limit OFFSET :offset";
        map.put("limit",orderQueryParam.getLimit());
        map.put("offset",orderQueryParam.getOffset());

        List<Orders>orderList=namedParameterJdbcTemplate.query(sql,map,new OrderRowMapper());
        return orderList;
    }

    @Override
    public List<OrderItemResponse> getOrderItemsByOrderIdList(List<Integer> orderIdList) {
        String sql= """
                SELECT oi.order_item_id, oi.order_id, oi.quantity, oi.price, (oi.quantity * oi.price) AS subtotal,
                oi.product_id, oi.product_name, p.image_url
                FROM order_item AS oi
                JOIN orders AS o ON oi.order_id= o.order_id
                JOIN product AS p ON oi.product_id=p.product_id
                WHERE oi.order_id IN (:orderIdList)""";

        Map<String,Object> map=new HashMap<>();
        map.put("orderIdList",orderIdList);

        List<OrderItemResponse>orderItems=namedParameterJdbcTemplate.query(sql,map, new OrderItemResponseRowMapper());

        return orderItems;
    }
}
