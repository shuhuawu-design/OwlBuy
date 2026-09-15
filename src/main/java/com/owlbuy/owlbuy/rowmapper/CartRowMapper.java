package com.owlbuy.owlbuy.rowmapper;

import com.owlbuy.owlbuy.model.Cart;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CartRowMapper implements RowMapper<Cart> {
    @Override
    public Cart mapRow(ResultSet rs, int rowNum) throws SQLException {
        Cart cart=new Cart();
        cart.setCartId(rs.getInt("cart_id"));
        cart.setMemberId(rs.getInt("member_id"));
        cart.setCreated_date(rs.getDate("created_date"));


        return cart;
    }
}
