package com.fsse2203.project_backend.service.impl;

import com.fsse2203.project_backend.data.cart.entity.CartItemEntity;
import com.fsse2203.project_backend.data.transaction.TransactionData;
import com.fsse2203.project_backend.data.transaction.data.TransactionStatusEnum;
import com.fsse2203.project_backend.data.transaction.data.entity.TransactionEntity;
import com.fsse2203.project_backend.data.transaction.data.entity.TransactionProductEntity;
import com.fsse2203.project_backend.data.user.entity.UserEntity;
import com.fsse2203.project_backend.exception.*;
import com.fsse2203.project_backend.reposotory.TransactionRepository;
import com.fsse2203.project_backend.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);
    private final UserService userService;
    private final CartItemService cartItemService;
    private final TransactionRepository transactionRepository;
    private final TransactionProductService transactionProductService;
    private final ProductService productService;

    @Autowired
    public TransactionServiceImpl (UserService userService, CartItemService cartItemService,
                                   TransactionRepository transactionRepository,
                                   TransactionProductService transactionProductService,
                                   ProductService productService) {
        this.userService = userService;
        this.cartItemService = cartItemService;
        this.transactionRepository = transactionRepository;
        this.transactionProductService = transactionProductService;
        this.productService = productService;
    }

    @Override
    public TransactionData createTransaction (String firebaseUid, long dateTime)
            throws EmptyCartException, UnfinishedTransactionExistsException {
        UserEntity userEntity = userService.getUserEntityByFirebaseUid(firebaseUid);
        logger.warn("got user info");
        checkUnfinishedTransaction(userEntity);
        logger.warn("passed checking, all transactions are finished");
        TransactionEntity transactionEntity = transactionRepository.save(new TransactionEntity(userEntity, dateTime));

        List<CartItemEntity> cartItemEntities = cartItemService.getCartItemEntities(userEntity);
        if (cartItemEntities.isEmpty()) {
            logger.warn("empty cart exception");
            throw new EmptyCartException();
        }
        List<TransactionProductEntity> transactionProductEntities = transactionProductService.createTransactionProducts(transactionEntity, cartItemEntities);

        return new TransactionData(transactionEntity, transactionProductEntities);
    }

    public void checkUnfinishedTransaction (UserEntity userEntity) throws UnfinishedTransactionExistsException {
        List<TransactionEntity> transactionEntities = transactionRepository.findAllByUser_Uid(userEntity.getUid());
        if (!transactionEntities.isEmpty()) {
            for (TransactionEntity transactionEntity : transactionEntities) {
                if (!(transactionEntity.getStatus() == TransactionStatusEnum.SUCCESS)) {
                    logger.warn("failed checking: users have unfinished orders");
                    throw new UnfinishedTransactionExistsException();
                }
            }
        }
    }

    @Override
    public TransactionData getTransactionDetailsByTid (String firebaseUid, Integer tid)
            throws TransactionNotFoundException, TransactionProductNotFound {

        TransactionEntity transactionEntity = checkTidAndUser(firebaseUid, tid);

        List<TransactionProductEntity> transactionProductEntities
                = transactionProductService.getTransactionProductsByTid(transactionEntity);

        transactionEntity.setTotalPrice(calculateTotalPrice(transactionProductEntities));
        TransactionEntity updatedTransactionEntity = transactionRepository.save(transactionEntity);
        return new TransactionData(updatedTransactionEntity, transactionProductEntities);
    }

    @Override
    public TransactionData updateTransactionStatusToProcessingByTid(String firebaseUid, Integer tid)
            throws TransactionNotFoundException, ProductNotFoundException, NotEnoughStockException, TransactionProductNotFound {
        logger.warn("entered update status to processing method");
        TransactionEntity transactionEntity = checkTidAndUser(firebaseUid, tid);
        logger.warn("successfully found Tid and User");
        List<TransactionProductEntity> transactionProductEntities
                = transactionProductService.getTransactionProductsByTid(transactionEntity);

        if (transactionProductEntities.isEmpty()){
            logger.warn("transactionProductEntities is empty");
        }
        productService.reduceStock(transactionProductEntities);

        transactionEntity.setTotalPrice(calculateTotalPrice(transactionProductEntities));
        transactionEntity.setStatus(TransactionStatusEnum.PROCESSING);
        TransactionEntity updatedTransactionEntity = transactionRepository.save(transactionEntity);

        return new TransactionData(updatedTransactionEntity, transactionProductEntities);
    }

    public TransactionEntity checkTidAndUser(String firebaseUid, Integer tid) throws TransactionNotFoundException {
        TransactionEntity transactionEntity = transactionRepository.findByTidAndUserFirebaseUid(tid, firebaseUid);
        if (transactionEntity == null) {
            logger.warn("TransactionNotFoundException exception");
            throw new TransactionNotFoundException();
        }
        return transactionEntity;
    }

    public BigDecimal calculateTotalPrice (List<TransactionProductEntity> transactionProductEntities) {
        BigDecimal total = BigDecimal.ZERO;
        for (TransactionProductEntity tpEntity: transactionProductEntities) {
            total = total.add(tpEntity.getSubTotal());
        }
        logger.warn("calculate total price");
        return total;
    }

    @Override
    public TransactionData finishTransaction (String firebaseUid , Integer tid) throws TransactionNotFoundException, TransactionProductNotFound {

        TransactionEntity transactionEntity = checkTidAndUser(firebaseUid, tid);

        List<TransactionProductEntity> transactionProductEntities
                = transactionProductService.getTransactionProductsByTid(transactionEntity);
        logger.warn("found entity list");

        transactionEntity.setTotalPrice(calculateTotalPrice(transactionProductEntities));
        transactionEntity.setStatus(TransactionStatusEnum.SUCCESS);
        TransactionEntity updatedTransactionEntity = transactionRepository.save(transactionEntity);
        logger.warn("saved entity");
        logger.warn(updatedTransactionEntity.getUser().getEmail());
        cartItemService.deleteItemsByUserEntity(updatedTransactionEntity.getUser());
        logger.warn("delete item by user entity");
        return new TransactionData(updatedTransactionEntity, transactionProductEntities);
    }
}
