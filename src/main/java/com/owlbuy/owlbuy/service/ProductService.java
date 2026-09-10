package com.owlbuy.owlbuy.service;

import com.owlbuy.owlbuy.dto.ProductRequest;
import com.owlbuy.owlbuy.model.Product;

public interface ProductService {
    Integer createProduct(ProductRequest productRequest);
    Product getProductById(Integer productId);
    void updateProduct(Integer productId, ProductRequest productRequest);
    void deleteProductById(Integer productId);
}
