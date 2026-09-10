package com.owlbuy.owlbuy.dao;

import com.owlbuy.owlbuy.dto.ProductRequest;
import com.owlbuy.owlbuy.model.Product;

public interface ProductDao {
    Integer createProduct(ProductRequest productRequest);
    Product getProductById(Integer productId);
}
