package com.fsse2203.project_backend.reposotory;

import com.fsse2203.project_backend.data.cart.entity.CartItemEntity;
import com.fsse2203.project_backend.data.transaction.data.entity.TransactionEntity;
import com.fsse2203.project_backend.data.transaction.data.entity.TransactionProductEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionProductRepository extends CrudRepository<TransactionProductEntity, Integer> {

    List<TransactionProductEntity> findByTransaction (@Param("transaction")TransactionEntity transaction);
}
