package com.owlbuy.owlbuy.rowmapper;

import com.owlbuy.owlbuy.constant.ProductStatus;
import com.owlbuy.owlbuy.dto.CartItemResponse;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CartItemResponseRowMapper implements RowMapper<CartItemResponse> {
    public CartItemResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
        CartItemResponse cartItemResponse=new CartItemResponse();
        cartItemResponse.setCartItemId(rs.getInt("cart_item_id"));
        cartItemResponse.setQuantity(rs.getInt("quantity"));
        cartItemResponse.setSubtotal(rs.getBigDecimal("subtotal"));
        cartItemResponse.setProductId(rs.getInt("product_id"));
        cartItemResponse.setProductName(rs.getString("product_name"));
        cartItemResponse.setPrice(rs.getBigDecimal("price"));
        cartItemResponse.setImageUrl(rs.getString("image_url"));

        String s=rs.getString("status");
        ProductStatus status=ProductStatus.valueOf(s);
        cartItemResponse.setStatus(status);

        return cartItemResponse;

    }
}
