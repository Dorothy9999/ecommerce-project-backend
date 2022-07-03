package com.fsse2203.project_backend.data.transaction.data.entity;

import com.fsse2203.project_backend.data.transaction.data.TransactionStatusEnum;
import com.fsse2203.project_backend.data.user.entity.UserEntity;
import org.apache.catalina.User;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "transaction")
public class TransactionEntity {
    @Id
    @Column(name = "tid", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer tid;

    @ManyToOne
    @JoinColumn(name = "uid", nullable = false)
    private UserEntity user;

    @Column(name = "datetime", nullable = false)
    private long datetime;

    @Enumerated(EnumType.STRING)
    private TransactionStatusEnum status;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    public TransactionEntity () {}

    public TransactionEntity (UserEntity userEntity, long datetime) {
        this.user = userEntity;
        this.datetime = datetime;
        setStatus(TransactionStatusEnum.PREPARE);
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
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
}
