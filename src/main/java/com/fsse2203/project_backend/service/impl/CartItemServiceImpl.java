package com.fsse2203.project_backend.service.impl;

import com.fsse2203.project_backend.data.cart.CartItemRequestData;
import com.fsse2203.project_backend.data.cart.CartItemsResponseData;
import com.fsse2203.project_backend.data.cart.entity.CartItemEntity;
import com.fsse2203.project_backend.data.product.entity.ProductEntity;
import com.fsse2203.project_backend.data.user.entity.UserEntity;
import com.fsse2203.project_backend.exception.*;
import com.fsse2203.project_backend.reposotory.CartItemRepository;
import com.fsse2203.project_backend.reposotory.SQLCartItemRepository;
import com.fsse2203.project_backend.service.CartItemService;
import com.fsse2203.project_backend.service.ProductService;
import com.fsse2203.project_backend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartItemServiceImpl implements CartItemService {

    Logger logger = LoggerFactory.getLogger(CartItemServiceImpl.class);
    private final ProductService productService;
    private final UserService userService;
    private final CartItemRepository cartItemRepository;
    private final SQLCartItemRepository sqlCartItemRepository;

    @Autowired
    public CartItemServiceImpl(ProductService productService, UserService userService, CartItemRepository cartItemRepository,
                               SQLCartItemRepository sqlCartItemRepository) {
        this.productService = productService;
        this.userService = userService;
        this.cartItemRepository = cartItemRepository;
        this.sqlCartItemRepository = sqlCartItemRepository;
    }

    @Override
    public boolean addItemToCart (CartItemRequestData cartItemData)
            throws ProductNotFoundException, QuantityNumberNotValidException, NotEnoughStockException, UserNotFoundExceptionException {

        UserEntity userEntity = userService.getUserEntityByFirebaseUid(cartItemData.getFirebaseUid());
        if (userEntity == null) { //why still add even you are sure it must exist in database?to prevent others mistakes,becoz it's a group project
            throw new UserNotFoundExceptionException();
        }
        ProductEntity productEntity = productService.findProductEntity(cartItemData.getPid());

//        CartItemEntity foundEntity = sqlCartItemRepository.findByProduct_PidAndUser_Uid(productEntity.getPid(), userEntity.getUid());
        CartItemEntity foundEntity = cartItemRepository.findByProductAndUser(productEntity, userEntity);
        if (foundEntity != null) { //if same user add same cartItem again:
            Integer sumQty = foundEntity.getQuantity() + cartItemData.getQuantity();
            sumQty = checkAddQuantityValid(sumQty, productEntity);
            foundEntity.setQuantity(sumQty);
            cartItemRepository.save(foundEntity);
            return true;
        } else { //if the cart is new:
            Integer quantity = checkAddQuantityValid(cartItemData.getQuantity(), productEntity);
            cartItemRepository.save(new CartItemEntity(productEntity, userEntity, quantity));
        }
        return true;
    }

    public Integer checkAddQuantityValid(Integer finalQty, ProductEntity productEntity)
            throws QuantityNumberNotValidException, NotEnoughStockException {
        if (finalQty < 1 ) {
            throw new QuantityNumberNotValidException();
        } else if (finalQty > productEntity.getStock()) {
            logger.warn("input qty is smaller than stock");
            throw new NotEnoughStockException();
        }
        return finalQty;
    }

    @Override
    public List<CartItemsResponseData> getCartItems (String firebaseUid) {
        UserEntity userEntity = userService.getUserEntityByFirebaseUid(firebaseUid);

        List<CartItemsResponseData> cartItemsDataList = new ArrayList<>();
        List<CartItemEntity> cartItemEntities = getCartItemEntities(userEntity);
        for (CartItemEntity entity : cartItemEntities) {
            cartItemsDataList.add(new CartItemsResponseData(entity));
        }

        return cartItemsDataList;
    }

    @Override
    public List<CartItemEntity> getCartItemEntities (UserEntity userEntity) {

//native sql:    return sqlCartItemRepository.findByUser(userEntity);

        return cartItemRepository.findByUser(userEntity);
    }

    @Override
    public CartItemsResponseData updateCartItemQty (CartItemRequestData cartItemData)
        throws ProductNotFoundException, QuantityNumberNotValidException, ProductNotExistsInCartException,
            NotEnoughStockException {

        ProductEntity productEntity = productService.findProductEntity(cartItemData.getPid());
        Integer checkedQuantity = checkUpdateQuantityValid(cartItemData.getQuantity(), productEntity);
        UserEntity userEntity = userService.getUserEntityByFirebaseUid(cartItemData.getFirebaseUid());

        CartItemEntity foundEntity = cartItemRepository.findByProductAndUser(productEntity, userEntity);
        if (foundEntity != null) {
            foundEntity.setQuantity(checkedQuantity);
            CartItemEntity cartItem = cartItemRepository.save(foundEntity);
            return new CartItemsResponseData(cartItem);
        } else {
            throw new ProductNotExistsInCartException();
        }
    }

    public Integer checkUpdateQuantityValid(Integer quantity, ProductEntity productEntity)
            throws QuantityNumberNotValidException, NotEnoughStockException {
        if (quantity < 1 ) {
            throw new QuantityNumberNotValidException();
        } else if (quantity > productEntity.getStock()) {
            logger.warn("input qty is smaller than stock");
            throw new NotEnoughStockException();
        }
        return quantity;
    }

    @Override
    public boolean deleteCartItem (String firebaseUid, Integer pid) throws ProductNotFoundException, ProductNotExistsInCartException{
        ProductEntity productEntity = productService.findProductEntity(pid);
        UserEntity userEntity = userService.getUserEntityByFirebaseUid(firebaseUid);

        CartItemEntity foundEntity = cartItemRepository.findByProductAndUser(productEntity, userEntity);
        if (foundEntity != null) {
            cartItemRepository.delete(foundEntity);
            return true;
        } else {
            throw new ProductNotExistsInCartException();
        }
    }

    @Override
    public void deleteItemsByUserEntity (UserEntity userEntity) {
        logger.warn("successfully enter delete item method");
        Iterable<CartItemEntity> cartItemEntities = cartItemRepository.findByUser(userEntity);
        cartItemRepository.deleteAll(cartItemEntities);
        //cartItemRepository.deleteAllByUser(userEntity); this doesn't work
        logger.warn("deleted cart items");
    }
}
