package com.fsse2203.project_backend.service;

import com.fsse2203.project_backend.data.cart.CartItemRequestData;
import com.fsse2203.project_backend.data.cart.CartItemsResponseData;
import com.fsse2203.project_backend.data.cart.entity.CartItemEntity;
import com.fsse2203.project_backend.data.user.entity.UserEntity;
import com.fsse2203.project_backend.exception.*;

import java.util.List;

public interface CartItemService {
    boolean addItemToCart (CartItemRequestData cartItemData)
            throws ProductNotFoundException, QuantityNumberNotValidException, NotEnoughStockException, UserNotFoundExceptionException;
    List<CartItemsResponseData> getCartItems (String firebaseUid);
    CartItemsResponseData updateCartItemQty (CartItemRequestData cartItemData)
            throws ProductNotFoundException, QuantityNumberNotValidException, ProductNotExistsInCartException, NotEnoughStockException;
    boolean deleteCartItem (String firebaseUid, Integer pid) throws ProductNotFoundException, ProductNotExistsInCartException;
    List<CartItemEntity> getCartItemEntities (UserEntity userEntity);
    void deleteItemsByUserEntity (UserEntity userEntity);
}
