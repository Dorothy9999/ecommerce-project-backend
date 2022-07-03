package com.fsse2203.project_backend.data.product.entity;

import com.fsse2203.project_backend.data.product.ProductDetailsRequestData;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "pruduct")
public class ProductEntity {
    @Id
    @Column(name = "pid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer pid;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "stock", nullable = false)
    private Integer stock;
    //why don't use int as data type?
    //becoz when the staff didn't input for stock, it will turn 0, becoz int's default value is 0

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    public ProductEntity () {}

    public ProductEntity (ProductDetailsRequestData data) {
        this.name = data.getName();
        this.description = data.getDescription();
        this.imageUrl = data.getImageUrl();
        this.price = data.getPrice();
        this.stock = data.getStock();
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
