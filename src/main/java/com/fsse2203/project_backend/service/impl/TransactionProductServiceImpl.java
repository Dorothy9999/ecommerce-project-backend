package com.fsse2203.project_backend.service.impl;

import com.fsse2203.project_backend.data.cart.entity.CartItemEntity;
import com.fsse2203.project_backend.data.transaction.data.entity.TransactionEntity;
import com.fsse2203.project_backend.data.transaction.data.entity.TransactionProductEntity;
import com.fsse2203.project_backend.exception.TransactionProductNotFound;
import com.fsse2203.project_backend.reposotory.TransactionProductRepository;
import com.fsse2203.project_backend.service.TransactionProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionProductServiceImpl implements TransactionProductService {

    Logger logger = LoggerFactory.getLogger(TransactionProductServiceImpl.class);
    private final TransactionProductRepository transactionProductRepository;

    @Autowired
    public TransactionProductServiceImpl (TransactionProductRepository transactionProductRepository) {
        this.transactionProductRepository = transactionProductRepository;
    }

    @Override
    public List<TransactionProductEntity> createTransactionProducts
            (TransactionEntity transactionEntity, List<CartItemEntity> cartItemEntities) {

        List<TransactionProductEntity> transactionProductEntities = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;
        for (CartItemEntity cartItemEntity: cartItemEntities) {
            TransactionProductEntity transactionProductEntity = new TransactionProductEntity(cartItemEntity, transactionEntity);
            TransactionProductEntity tpEntity = transactionProductRepository.save(transactionProductEntity);
            transactionProductEntities.add(tpEntity);
            total = total.add(tpEntity.getSubTotal());
        }
        transactionEntity.setTotalPrice(total);

        return transactionProductEntities;
    }

    @Override
    public List<TransactionProductEntity> getTransactionProductsByTid (TransactionEntity transactionEntity)
        throws TransactionProductNotFound{

        logger.warn("entered transaction product service, get Transaction Products by ID method");
        List<TransactionProductEntity> transactionProductEntities = transactionProductRepository.findByTransaction(transactionEntity);
        if (transactionProductEntities.isEmpty()) {
            logger.warn("TransactionProductNotFoundException");
            throw new TransactionProductNotFound();
        }

        return transactionProductEntities;
    }

}
