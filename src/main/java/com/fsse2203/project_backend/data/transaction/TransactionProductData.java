package com.fsse2203.project_backend.data.transaction;

import com.fsse2203.project_backend.data.product.ProductDetailsData;
import com.fsse2203.project_backend.data.transaction.data.entity.TransactionProductEntity;

import java.math.BigDecimal;

public class TransactionProductData {
    private Integer tpid;
    private ProductDetailsData product;
    private Integer tpQuantity;
    private BigDecimal subTotal;

    public TransactionProductData(TransactionProductEntity entity) {
        this.tpid = entity.getTpid();
        this.tpQuantity = entity.getTpQuantity();
        this.subTotal = entity.getSubTotal();
        setProduct(entity);
    }

    public Integer getTpid() {
        return tpid;
    }

    public void setTpid(Integer tpid) {
        this.tpid = tpid;
    }

    public ProductDetailsData getProduct() {
        return product;
    }

    public void setProduct(TransactionProductEntity entity) {
        this.product = new ProductDetailsData(entity);
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

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }
}
