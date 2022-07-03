package com.fsse2203.project_backend.data.product;

import com.fsse2203.project_backend.data.product.entity.ProductEntity;

import java.math.BigDecimal;

public class GetAllProductsData {
    private Integer pid;
    private String name;
    private String imageUrl;
    private BigDecimal price;
    private Boolean hasStock = false;

    public GetAllProductsData (ProductEntity entity) {
        this.pid = entity.getPid();
        this.name = entity.getName();
        this.imageUrl = entity.getImageUrl();
        this.price = entity.getPrice();
        setHasStock(entity);
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

    public Boolean getHasStock() {
        return hasStock;
    }

    public void setHasStock(ProductEntity entity) {
        if (entity.getStock() > 0 ) {
            this.hasStock = true;
        }
    }
}
