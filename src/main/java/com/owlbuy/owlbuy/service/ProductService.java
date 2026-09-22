package com.owlbuy.owlbuy.service;

import com.owlbuy.owlbuy.dto.ProductQueryParam;
import com.owlbuy.owlbuy.dto.ProductRequest;
import com.owlbuy.owlbuy.dto.ProductUpdateRequest;
import com.owlbuy.owlbuy.model.Product;
import com.owlbuy.owlbuy.util.Page;

public interface ProductService {
    Product createProduct(ProductRequest productRequest);
    Product getProductById(Integer productId);
    Page<Product> getProducts(ProductQueryParam productQueryParam);
    void updateProduct(Integer productId, ProductUpdateRequest productUpdateRequest);
    void deleteProductById(Integer productId);
}
