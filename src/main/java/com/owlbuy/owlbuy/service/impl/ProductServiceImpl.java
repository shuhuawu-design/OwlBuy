package com.owlbuy.owlbuy.service.impl;

import com.owlbuy.owlbuy.dao.ProductDao;
import com.owlbuy.owlbuy.dto.ProductQueryParam;
import com.owlbuy.owlbuy.dto.ProductRequest;
import com.owlbuy.owlbuy.dto.ProductUpdateRequest;
import com.owlbuy.owlbuy.model.Product;
import com.owlbuy.owlbuy.service.ProductService;
import com.owlbuy.owlbuy.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Component
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;

    @Transactional
    @Override
    public Product createProduct(ProductRequest productRequest) {
        Integer productId=productDao.createProduct(productRequest);

        return productDao.getProductById(productId);
    }

    @Override
    public Product getProductById(Integer productId) {
        Product product = productDao.getProductById(productId);
        if(product ==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"商品不存在:"+ productId);
        }
        return product;
    }

    @Override
    public Page<Product> getProducts(ProductQueryParam productQueryParam) {
        List<Product>productList=productDao.getProducts(productQueryParam);
        Integer total=productDao.countProducts(productQueryParam);

        Page<Product>page=new Page<>();
        page.setLimit(productQueryParam.getLimit());
        page.setOffset(productQueryParam.getOffset());
        page.setTotal(total);
        page.setList(productList);
        return page;
    }


    @Transactional
    @Override
    public void updateProduct(Integer productId, ProductUpdateRequest productUpdateRequest) {
        Product product=productDao.getProductById(productId);
        if(product == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"商品不存在:"+ productId);
        }else{
            productDao.updateProduct(productId, productUpdateRequest);
        }

    }

    @Override
    public void deleteProductById(Integer productId) {
        productDao.deleteProductById(productId);
    }
}
