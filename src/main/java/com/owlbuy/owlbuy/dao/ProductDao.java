package com.owlbuy.owlbuy.dao;

import com.owlbuy.owlbuy.dto.ProductQueryParam;
import com.owlbuy.owlbuy.dto.ProductRequest;
import com.owlbuy.owlbuy.dto.ProductUpdateRequest;
import com.owlbuy.owlbuy.model.Product;

import java.util.List;

public interface ProductDao {
    Integer createProduct(ProductRequest productRequest);
    Product getProductById(Integer productId);
    List<Product>getProducts(ProductQueryParam productQueryParam);
    Integer countProducts(ProductQueryParam productQueryParam);
    void updateProduct(Integer productId, ProductUpdateRequest productUpdateRequest);
    void deleteProductById(Integer productId);
}
