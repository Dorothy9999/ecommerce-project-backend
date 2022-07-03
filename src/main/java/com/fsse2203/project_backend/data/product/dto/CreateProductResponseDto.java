package com.fsse2203.project_backend.data.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fsse2203.project_backend.data.product.ProductDetailsData;

import java.math.BigDecimal;

public class CreateProductResponseDto {
    @JsonProperty("product_id")
    private Integer pid;
    @JsonProperty("product_name")
    private String name;
    @JsonProperty("product_description")
    private String description;
    @JsonProperty("product_imageUrl")
    private String imageUrl;
    @JsonProperty("product_stock")
    private Integer stock;
    @JsonProperty("product_price")
    private BigDecimal price;

    public CreateProductResponseDto(ProductDetailsData data) {
        this.pid = data.getPid();
        this.name = data.getName();
        this.description = data.getDescription();
        this.imageUrl = data.getImageUrl();
        this.stock = data.getStock();
        this.price = data.getPrice();
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
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
