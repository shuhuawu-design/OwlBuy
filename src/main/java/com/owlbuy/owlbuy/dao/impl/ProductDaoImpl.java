package com.owlbuy.owlbuy.dao.impl;

import com.owlbuy.owlbuy.dao.ProductDao;
import com.owlbuy.owlbuy.dto.ProductRequest;
import com.owlbuy.owlbuy.model.Product;
import com.owlbuy.owlbuy.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProductDaoImpl implements ProductDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        String sql = "INSERT INTO product(product_name,category,price,stock,image_url,description) VALUES (:product_name,:category,:price,:stock,:image_url,:description)";
        Map<String, Object> map = new HashMap<>();
        map.put("product_name", productRequest.getProductName());
        map.put("category", productRequest.getCategory().name());
        map.put("price", productRequest.getPrice());
        map.put("stock", productRequest.getStock());
        map.put("image_url", productRequest.getImageUrl());
        map.put("description", productRequest.getDescription());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        Integer productId = keyHolder.getKey().intValue();
        return productId;
    }

    @Override
    public Product getProductById(Integer productId) {
        String sql = "SELECT product_id, product_name,category, price, stock, image_url, description, created_date, updated_date FROM product WHERE product_id=:productId";
        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);

        List<Product> product = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());
        if(product.size()>0){
            return product.get(0);
        }else{
            return null;
        }
    }
}
