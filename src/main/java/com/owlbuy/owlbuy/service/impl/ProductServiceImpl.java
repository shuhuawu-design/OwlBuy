package com.owlbuy.owlbuy.service.impl;

import com.owlbuy.owlbuy.dao.ProductDao;
import com.owlbuy.owlbuy.dto.ProductQueryParam;
import com.owlbuy.owlbuy.dto.ProductRequest;
import com.owlbuy.owlbuy.dto.ProductUpdateRequest;
import com.owlbuy.owlbuy.model.Product;
import com.owlbuy.owlbuy.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

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
    public List<Product> getProducts(ProductQueryParam productQueryParam) {
        List<Product>productList=productDao.getProducts(productQueryParam);
        return productList;
    }

    @Override
    public Integer countProducts(ProductQueryParam productQueryParam) {
        Integer total=productDao.countProducts(productQueryParam);
        return total;
    }

    @Override
    public void updateProduct(Integer productId, ProductUpdateRequest productUpdateRequest) {
        productDao.updateProduct(productId, productUpdateRequest);
    }

    @Override
    public void deleteProductById(Integer productId) {
        productDao.deleteProductById(productId);
    }
}
