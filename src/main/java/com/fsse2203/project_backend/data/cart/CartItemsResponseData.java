package com.fsse2203.project_backend.data.cart;

import com.fsse2203.project_backend.data.cart.entity.CartItemEntity;
import com.fsse2203.project_backend.data.product.ProductDetailsData;
import com.fsse2203.project_backend.data.product.entity.ProductEntity;

public class CartItemsResponseData {
    private ProductDetailsData product;
    private Integer cartQuantity;

    public CartItemsResponseData(CartItemEntity entity) {
        setProduct(entity.getProduct());
        this.cartQuantity = entity.getQuantity();
    }

    public ProductDetailsData getProduct() {
        return product;
    }

    public void setProduct(ProductEntity entity) {
        this.product = new ProductDetailsData(entity);
    }

    public Integer getCartQuantity() {
        return cartQuantity;
    }

    public void setCartQuantity(Integer cartQuantity) {
        this.cartQuantity = cartQuantity;
    }
}
