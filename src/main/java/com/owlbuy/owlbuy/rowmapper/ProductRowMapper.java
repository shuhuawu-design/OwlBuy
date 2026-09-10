package com.owlbuy.owlbuy.rowmapper;

import com.owlbuy.owlbuy.constant.ProductCategory;
import com.owlbuy.owlbuy.model.Product;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class ProductRowMapper implements RowMapper<Product> {
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        Product product = new Product();
        product.setProductId(rs.getInt("product_id"));
        product.setProductName(rs.getString("product_name"));

        String s=(rs.getString("category"));
        ProductCategory category=ProductCategory.valueOf(s);
        product.setCategory(category);

        product.setPrice(rs.getBigDecimal("price"));
        product.setStock(rs.getInt("stock"));
        product.setImageUrl(rs.getString("image_url"));
        product.setDescription(rs.getString("description"));
        product.setCreatedDate(rs.getDate("created_date"));
        product.setUpdatedDate(rs.getDate("updated_date"));



        return product;
    }
}
