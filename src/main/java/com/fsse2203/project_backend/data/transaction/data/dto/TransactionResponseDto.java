package com.fsse2203.project_backend.data.transaction.data.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fsse2203.project_backend.data.product.ProductDetailsData;
import com.fsse2203.project_backend.data.product.dto.GetAProductResponseDto;
import com.fsse2203.project_backend.data.transaction.TransactionData;
import com.fsse2203.project_backend.data.transaction.TransactionProductData;
import com.fsse2203.project_backend.data.transaction.data.TransactionStatusEnum;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

public class TransactionResponseDto {
    private Integer tid;
    @JsonProperty("buyer_uid")
    private Integer uid;
    //@JsonFormat(pattern = "yyyyMMdd'T'HH:mm:ss") 這個方法也可以
    private String datetime;
    private TransactionStatusEnum status;
    @JsonProperty("total")
    private BigDecimal totalPrice;
    private List<TransactionProductResponseDto> items;

    public TransactionResponseDto(TransactionData data) {
        this.tid = data.getTid();
        this.uid = data.getUid();
        this.status = data.getStatus();
        this.totalPrice = data.getTotalPrice();
        setDatetime(data.getDatetime());
        setItems(data.getItems());
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

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(long datetime) {

        Timestamp timestamp = new Timestamp(datetime);
        SimpleDateFormat customedFormat = new SimpleDateFormat("yyyyMMdd'T'HH:mm:ss");
        String formatDatetime = customedFormat.format(timestamp);
        this.datetime = formatDatetime;
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

    public List<TransactionProductResponseDto> getItems() {
        return items;
    }

    public void setItems(List<TransactionProductData> items) {
        List<TransactionProductResponseDto> itemsDtoList = new ArrayList<>();
        for (TransactionProductData data : items) {
            itemsDtoList.add(new TransactionProductResponseDto(data));
        }
        this.items = itemsDtoList;
    }
}
