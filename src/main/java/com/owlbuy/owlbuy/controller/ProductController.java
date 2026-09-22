package com.owlbuy.owlbuy.controller;

import com.owlbuy.owlbuy.constant.ProductCategory;
import com.owlbuy.owlbuy.dto.ProductQueryParam;
import com.owlbuy.owlbuy.dto.ProductRequest;
import com.owlbuy.owlbuy.dto.ProductUpdateRequest;
import com.owlbuy.owlbuy.model.Product;
import com.owlbuy.owlbuy.service.ProductService;
import com.owlbuy.owlbuy.util.Page;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest productRequest) {
        Product product=productService.createProduct(productRequest);

        return ResponseEntity.ok().body(product);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Integer productId) {
        Product product=productService.getProductById(productId);

        return ResponseEntity.ok().body(product);
    }

    @GetMapping("/products")
    public ResponseEntity<Page<Product>> getProducts(
            @RequestParam(name="category",required = false) ProductCategory category,
            @RequestParam(name="search",required = false) String search,
            @RequestParam(defaultValue = "created_date") String orderBy,
            @RequestParam(defaultValue = "desc") String sort,
            @RequestParam(defaultValue = "20")@Max(100)@Min(0) Integer limit,
            @RequestParam(defaultValue = "0")@Min(0) Integer offset) {
        ProductQueryParam productQueryParam=new ProductQueryParam();
        productQueryParam.setCategory(category);
        productQueryParam.setSearch(search);
        productQueryParam.setOrderBy(orderBy);
        productQueryParam.setSort(sort);
        productQueryParam.setLimit(limit);
        productQueryParam.setOffset(offset);

        Page<Product>page=productService.getProducts(productQueryParam);

        return ResponseEntity.ok().body(page);
    }



    @PutMapping("/products/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer productId,@RequestBody @Valid ProductUpdateRequest productUpdateRequest) {
        productService.updateProduct(productId,productUpdateRequest);
        return ResponseEntity.ok().build();

    }
    @DeleteMapping("/products/{productId}")
    public ResponseEntity<Product> deleteProduct(@PathVariable Integer productId) {
        productService.deleteProductById(productId);
        return ResponseEntity.noContent().build();
    }
}
