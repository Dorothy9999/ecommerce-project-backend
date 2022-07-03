package com.fsse2203.project_backend.reposotory;

import com.fsse2203.project_backend.data.cart.entity.CartItemEntity;
import com.fsse2203.project_backend.data.product.entity.ProductEntity;
import com.fsse2203.project_backend.data.user.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SQLCartItemRepository extends CrudRepository <CartItemEntity, Integer> {
    boolean existsByProductAndUser
            (@Param("product")ProductEntity product, @Param("user")UserEntity user);

    @Query(value = "SELECT * FROM cart_item c WHERE c.pid=?1 AND c.uid=?2",
            nativeQuery = true)
    CartItemEntity findByProduct_PidAndUser_Uid
            (@Param("pid")Integer pid, @Param("uid")Integer uid);
    //findByProduct_PidAndUser_Uid 中的Product是一個叫product的ProductEntity的object
    //"SELECT c FROM cart_item c WHERE c.pruduct.pid=?1 AND c.user.uid=?2" >> this is jpql format
    //when using native query, need to use column name you set!! but in JPQL, need to use class attribute name

    /* 這樣寫都得
    CartItemEntity findCartItemEntityByProductAndUser
     */

//    @Query("SELECT c FROM CartItemEntity c WHERE c.user.uid=user.uid")
//    List<CartItemEntity> findByUser (@Param("user") UserEntity user);
    //c.user=:user  >> failed
    //c.user=?1user >> failed
    //c.user=?1     >> failed
    //c.user.uid=?1 >> internal error
    //c.user.uid=?1.uid  >> failed
    //c.user.uid=user.uid  >> work too!!
    //@Query("SELECT c FROM CartItemEntity c WHERE c.user=user") JPA自己識搵PK對
    //尾2兩條run的東西query一樣
    //If you want to run the program faster, need to use the pid and uid to search the table, instead of an object
    //but then you need to receive Integer. More 中point/擊中目標


    /* 這樣寫都得
    List<CartItemEntity> findAllByUser(UserEntity user);
     */

    /*
    findByUser_FirebaseUidAndProduct_Pid   >>找UserEntity裏面的firebaseUid | ProductEntity的pid
     */

    void deleteAllByUser (@Param("user") UserEntity user);

}
