package com.fsse2203.project_backend.data.cart;

public class CartItemRequestData {
    private String firebaseUid;
    private Integer quantity;
    private Integer pid;

    public CartItemRequestData (String firebaseUid, Integer quantity, Integer pid) {
        this.firebaseUid = firebaseUid;
        this.pid = pid;
        this.quantity = quantity;
    }

    public String getFirebaseUid() {
        return firebaseUid;
    }

    public void setFirebaseUid(String firebaseUid) {
        this.firebaseUid = firebaseUid;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }
}
