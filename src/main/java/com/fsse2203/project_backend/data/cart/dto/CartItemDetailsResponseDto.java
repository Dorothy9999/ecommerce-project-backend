package com.fsse2203.project_backend.data.cart.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fsse2203.project_backend.data.cart.CartItemsResponseData;

import java.math.BigDecimal;

public class CartItemDetailsResponseDto {

    private Integer pid;
    private String name;
    @JsonProperty("image_url")
    private String imageUrl;
    private BigDecimal price;
    @JsonProperty("cart_quantity")
    private Integer cartQuantity;
    private Integer stock;

    public CartItemDetailsResponseDto(CartItemsResponseData data) {
        this.cartQuantity = data.getCartQuantity();
        this.pid = data.getProduct().getPid();
        this.name= data.getProduct().getName();
        this.imageUrl = data.getProduct().getImageUrl();
        this.price = data.getProduct().getPrice();
        this.stock = data.getProduct().getStock();
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getCartQuantity() {
        return cartQuantity;
    }

    public void setCartQuantity(Integer cartQuantity) {
        this.cartQuantity = cartQuantity;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
