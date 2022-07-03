package com.fsse2203.project_backend.data.cart.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CartItemSuccessMsgResponseDto {
    @JsonProperty("result")
    private String message;

    public CartItemSuccessMsgResponseDto(boolean isSuccess) {
        if (isSuccess) {
            setMessage("SUCCESS");
        }
    }

    public String getMessage() {
        return message;
    }

    private void setMessage(String message) {
        this.message = message;
    }
}
