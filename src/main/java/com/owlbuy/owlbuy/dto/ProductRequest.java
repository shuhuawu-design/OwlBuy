package com.owlbuy.owlbuy.dto;

import com.owlbuy.owlbuy.constant.ProductCategory;
import com.owlbuy.owlbuy.constant.ProductStatus;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Date;

public class ProductRequest {
    @NotNull
    private String productName;
    @NotNull
    private ProductCategory category;
    @NotNull
    private BigDecimal price;
    @NotNull
    private Integer stock;
    @NotNull
    private String imageUrl;
    private String description;
    private ProductStatus status;
    private Date createdDate;
    private Date updatedDate;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProductStatus getStatus() {
        return status;
    }

    public void setStatus(ProductStatus status) {
        this.status = status;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }
}
