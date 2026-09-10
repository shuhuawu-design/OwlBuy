package com.owlbuy.owlbuy.controller;

import com.owlbuy.owlbuy.dto.ProductRequest;
import com.owlbuy.owlbuy.model.Product;
import com.owlbuy.owlbuy.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest productRequest) {
        Integer productId=productService.createProduct(productRequest);
        Product product=productService.getProductById(productId);

        return ResponseEntity.ok().body(product);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Integer productId) {
        Product product=productService.getProductById(productId);
        if(product!=null){
            return ResponseEntity.ok().body(product);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer productId,@RequestBody @Valid ProductRequest productRequest) {
        Product product=productService.getProductById(productId);
        if(product == null){
            return ResponseEntity.notFound().build();
        }else{
            productService.updateProduct(productId, productRequest);
            Product updateProduct=productService.getProductById(productId);
            return ResponseEntity.ok().body(updateProduct);
        }

    }
    @DeleteMapping("products/{productId}")
    public ResponseEntity<Product> deleteProduct(@PathVariable Integer productId) {
        productService.deleteProductById(productId);
        return ResponseEntity.noContent().build();
    }
}
