package com.owlbuy.owlbuy.rowmapper;

import com.owlbuy.owlbuy.dto.OrderItemResponse;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderItemResponseRowMapper implements RowMapper<OrderItemResponse> {
    @Override
    public OrderItemResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
        OrderItemResponse orderItemResponse = new OrderItemResponse();
        orderItemResponse.setOrderId(rs.getInt("order_id"));
        orderItemResponse.setOrderItemId(rs.getInt("order_item_id"));
        orderItemResponse.setQuantity(rs.getInt("quantity"));
        orderItemResponse.setPrice(rs.getBigDecimal("price"));
        orderItemResponse.setSubtotal(rs.getBigDecimal("subtotal"));
        orderItemResponse.setProductId(rs.getInt("product_id"));
        orderItemResponse.setProductName(rs.getString("product_name"));
        orderItemResponse.setImageUrl(rs.getString("image_url"));

        return orderItemResponse;
    }
}
