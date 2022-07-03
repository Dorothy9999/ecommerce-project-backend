package com.fsse2203.project_backend.data.transaction.data.entity;

import com.fsse2203.project_backend.data.cart.entity.CartItemEntity;
import com.fsse2203.project_backend.data.product.entity.ProductEntity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "transaction_product")
public class TransactionProductEntity {
    @Id
    @Column(name = "tpid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer tpid;

    @Column(name = "pid", nullable = false)
    private Integer pid;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "quantity", nullable = false)
    private Integer tpQuantity;

    @Column(name = "subtotal", nullable = false)
    private BigDecimal subTotal;

    @ManyToOne
    @JoinColumn(name = "tid", nullable = false)
    private TransactionEntity transaction;

    public TransactionProductEntity () {}

    public TransactionProductEntity (CartItemEntity cartItemEntity, TransactionEntity transactionEntity) {
        this.pid = cartItemEntity.getProduct().getPid();
        this.name = cartItemEntity.getProduct().getName();
        this.description = cartItemEntity.getProduct().getDescription();
        this.imageUrl = cartItemEntity.getProduct().getImageUrl();
        this.price = cartItemEntity.getProduct().getPrice();
        this.stock = cartItemEntity.getProduct().getStock();
        this.tpQuantity = cartItemEntity.getQuantity();
        this.transaction = transactionEntity;
        setSubTotal();
    }

    public Integer getTpid() {
        return tpid;
    }

    public void setTpid(Integer tpid) {
        this.tpid = tpid;
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

    public Integer getTpQuantity() {
        return tpQuantity;
    }

    public void setTpQuantity(Integer tpQuantity) {
        this.tpQuantity = tpQuantity;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal() {
        this.subTotal = BigDecimal.valueOf(tpQuantity).multiply(price);
    }

    public TransactionEntity getTransaction() {
        return transaction;
    }

    public void setTransaction(TransactionEntity transaction) {
        this.transaction = transaction;
    }
}
