package com.fsse2203.project_backend.data.product;

import com.fsse2203.project_backend.data.product.dto.CreateProductRequestDto;

import java.math.BigDecimal;

public class ProductDetailsRequestData {

    private String name;
    private String description;
    private String imageUrl;
    private Integer stock;
    private BigDecimal price;

    public ProductDetailsRequestData (CreateProductRequestDto dto) {
        this.name = dto.getName();
        this.description = dto.getDescription();
        this.imageUrl = dto.getImageUrl();
        this.stock = dto.getStock();
        this.price = dto.getPrice();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
