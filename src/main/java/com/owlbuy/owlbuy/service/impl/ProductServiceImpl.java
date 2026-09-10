package com.owlbuy.owlbuy.service.impl;

import com.owlbuy.owlbuy.dao.ProductDao;
import com.owlbuy.owlbuy.dto.ProductRequest;
import com.owlbuy.owlbuy.model.Product;
import com.owlbuy.owlbuy.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        Integer productId=productDao.createProduct(productRequest);
        return productId;
    }

    @Override
    public Product getProductById(Integer productId) {
        Product product = productDao.getProductById(productId);
        return product;
    }

    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest) {
        productDao.updateProduct(productId, productRequest);
    }

    @Override
    public void deleteProductById(Integer productId) {
        productDao.deleteProductById(productId);
    }
}
