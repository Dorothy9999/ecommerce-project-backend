package com.fsse2203.project_backend.data.transaction.data.dto;

import com.fsse2203.project_backend.data.product.ProductDetailsData;
import com.fsse2203.project_backend.data.product.dto.GetAProductResponseDto;
import com.fsse2203.project_backend.data.transaction.TransactionProductData;

import java.math.BigDecimal;

public class TransactionProductResponseDto {
    private Integer tpid;
    private GetAProductResponseDto product;
    private Integer quantity;
    private BigDecimal subtotal;

    public TransactionProductResponseDto (TransactionProductData data) {
        this.tpid = data.getTpid();
        this.quantity = data.getTpQuantity();
        this.subtotal = data.getSubTotal();
        setProduct(data.getProduct());
    }

    public Integer getTpid() {
        return tpid;
    }

    public void setTpid(Integer tpid) {
        this.tpid = tpid;
    }

    public GetAProductResponseDto getProduct() {
        return product;
    }

    public void setProduct(ProductDetailsData product) {
        this.product = new GetAProductResponseDto(product);
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }
}
