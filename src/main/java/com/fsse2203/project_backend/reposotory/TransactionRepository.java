package com.fsse2203.project_backend.reposotory;

import com.fsse2203.project_backend.data.cart.entity.CartItemEntity;
import com.fsse2203.project_backend.data.transaction.data.entity.TransactionEntity;
import com.fsse2203.project_backend.data.user.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends CrudRepository<TransactionEntity, Integer> {
    List<TransactionEntity> findAllByUser_Uid (@Param("user") Integer uid);
    TransactionEntity findByTidAndUserFirebaseUid (@Param("tid") Integer tid, @Param("firebaseUid") String firebaseUid);
}
