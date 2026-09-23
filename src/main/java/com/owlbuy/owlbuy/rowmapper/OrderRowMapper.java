package com.owlbuy.owlbuy.rowmapper;

import com.owlbuy.owlbuy.constant.OrderStatus;
import com.owlbuy.owlbuy.constant.PaymentMethod;
import com.owlbuy.owlbuy.constant.ProductStatus;
import com.owlbuy.owlbuy.model.Orders;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderRowMapper implements RowMapper<Orders> {
    @Override
    public Orders mapRow(ResultSet rs, int rowNum) throws SQLException {
        Orders orders=new Orders();
        orders.setOrderId(rs.getInt("order_id"));
        orders.setOrderSn(rs.getString("order_sn"));
        orders.setMemberId(rs.getInt("member_id"));

        String r=(rs.getString("status"));
        OrderStatus status=OrderStatus.from(r.toUpperCase());
        orders.setStatus(status);

        orders.setTotalAmount(rs.getBigDecimal("total_amount"));
        String s=(rs.getString("payment_method"));
        PaymentMethod paymentMethod=PaymentMethod.valueOf(s.toUpperCase());
        orders.setPaymentMethod(paymentMethod);

        orders.setShippingName(rs.getString("shipping_name"));
        orders.setShippingPhone(rs.getString("shipping_phone"));
        orders.setShippingAddress(rs.getString("shipping_address"));
        orders.setCreatedDate(rs.getTimestamp("created_date"));
        orders.setUpdatedDate(rs.getTimestamp("updated_date"));

        return orders;
    }
}
