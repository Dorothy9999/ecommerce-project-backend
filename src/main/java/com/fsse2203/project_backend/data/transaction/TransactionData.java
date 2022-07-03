package com.fsse2203.project_backend.data.transaction;

import com.fsse2203.project_backend.data.product.ProductDetailsData;
import com.fsse2203.project_backend.data.transaction.data.TransactionStatusEnum;
import com.fsse2203.project_backend.data.transaction.data.entity.TransactionEntity;
import com.fsse2203.project_backend.data.transaction.data.entity.TransactionProductEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TransactionData {

    private Integer tid;
    private Integer uid;
    private long datetime;
    private TransactionStatusEnum status;
    private BigDecimal totalPrice;
    private List<TransactionProductData> items;
    private BigDecimal total;


    public TransactionData(TransactionEntity transactionEntity,
                           List<TransactionProductEntity> itemsEntities) {
        this.tid = transactionEntity.getTid();
        this.uid = transactionEntity.getUser().getUid();
        this.datetime = transactionEntity.getDatetime();
        this.status = transactionEntity.getStatus();
        this.totalPrice = transactionEntity.getTotalPrice();
        setItems(itemsEntities);
        this.total = transactionEntity.getTotalPrice();
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public long getDatetime() {
        return datetime;
    }

    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }

    public TransactionStatusEnum getStatus() {
        return status;
    }

    public void setStatus(TransactionStatusEnum status) {
        this.status = status;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<TransactionProductData> getItems() {
        return items;
    }

    public void setItems(List<TransactionProductEntity> itemsEntities) {
        List<TransactionProductData> transactionProductData = new ArrayList<>();
        for (TransactionProductEntity entity : itemsEntities) {
            transactionProductData.add(new TransactionProductData(entity));
        }
       this.items = transactionProductData;
    }
}
