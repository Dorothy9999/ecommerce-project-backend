package com.fsse2203.project_backend.reposotory;

import com.fsse2203.project_backend.data.cart.entity.CartItemEntity;
import com.fsse2203.project_backend.data.product.entity.ProductEntity;
import com.fsse2203.project_backend.data.user.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartItemRepository extends CrudRepository <CartItemEntity, Integer> {
    boolean existsByProductAndUser
            (@Param("product")ProductEntity product, @Param("user")UserEntity user);

    CartItemEntity findByProductAndUser
            (@Param("product")ProductEntity product, @Param("user")UserEntity user);
    /* 這樣寫都得
    CartItemEntity findCartItemEntityByProductAndUser
     */
    @Query("SELECT c FROM CartItemEntity c WHERE c.user= :user")
    List<CartItemEntity> findByUser (@Param("user") UserEntity user);

    /* 這樣寫都得
    List<CartItemEntity> findAllByUser(UserEntity user);
     */

    /*
    findByUser_FirebaseUidAndProduct_Pid   >>找UserEntity裏面的firebaseUid | ProductEntity的pid
     */

    void deleteAllByUser (@Param("user") UserEntity user);

}
