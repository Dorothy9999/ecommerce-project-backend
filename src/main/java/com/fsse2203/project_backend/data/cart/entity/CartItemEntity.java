package com.fsse2203.project_backend.data.cart.entity;

import com.fsse2203.project_backend.data.product.entity.ProductEntity;
import com.fsse2203.project_backend.data.user.entity.UserEntity;

import javax.persistence.*;

@Entity
@Table(name = "cart_item")
public class CartItemEntity {
    @Id
    @Column (name = "cid", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer cid;

    @OneToOne
    @JoinColumn(name = "pid", nullable = false)
    private ProductEntity product;

    @ManyToOne
    @JoinColumn(name = "uid", nullable = false)
    private UserEntity user;

    @Column (name = "quantity", nullable = false)
    private Integer quantity;

    public CartItemEntity() {}

    public CartItemEntity(ProductEntity productEntity, UserEntity userEntity, Integer quantity) {
        this.product = productEntity;
        this.user = userEntity;
        this.quantity = quantity;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
