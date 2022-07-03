package com.fsse2203.project_backend.service;

import com.fsse2203.project_backend.data.cart.entity.CartItemEntity;
import com.fsse2203.project_backend.data.transaction.data.entity.TransactionEntity;
import com.fsse2203.project_backend.data.transaction.data.entity.TransactionProductEntity;
import com.fsse2203.project_backend.exception.TransactionProductNotFound;

import java.util.List;

public interface TransactionProductService {
    List<TransactionProductEntity> createTransactionProducts
            (TransactionEntity transactionEntity, List<CartItemEntity> cartItemEntities);

    List<TransactionProductEntity> getTransactionProductsByTid (TransactionEntity transactionEntity)
            throws TransactionProductNotFound;
}
