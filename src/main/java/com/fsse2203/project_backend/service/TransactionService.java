package com.fsse2203.project_backend.service;

import com.fsse2203.project_backend.data.transaction.TransactionData;
import com.fsse2203.project_backend.exception.*;

public interface TransactionService {
    TransactionData createTransaction (String firebaseUid, long dateTime) throws EmptyCartException, UnfinishedTransactionExistsException;
    TransactionData getTransactionDetailsByTid (String firebaseUid, Integer tid)
            throws TransactionNotFoundException, TransactionProductNotFound;
    TransactionData updateTransactionStatusToProcessingByTid(String firebaseUid, Integer tid)
            throws TransactionNotFoundException, ProductNotFoundException, NotEnoughStockException, TransactionProductNotFound;
    TransactionData finishTransaction (String firebaseUid, Integer tid) throws TransactionNotFoundException, TransactionProductNotFound;
}
