package com.fsse2203.project_backend.api;

import com.fsse2203.project_backend.config.EnvConfig;
import com.fsse2203.project_backend.data.transaction.TransactionData;
import com.fsse2203.project_backend.data.transaction.data.dto.TransactionResponseDto;
import com.fsse2203.project_backend.exception.*;
import com.fsse2203.project_backend.service.TransactionService;
import com.fsse2203.project_backend.service.impl.TransactionServiceImpl;
import com.fsse2203.project_backend.util.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {EnvConfig.devBaseUrl,
        EnvConfig.prodBaseUrl},
        maxAge = 3600)
@RestController
@RequestMapping("/transaction")
public class TransactionApi {

    Logger logger = LoggerFactory.getLogger(TransactionApi.class);
    private final TransactionService transactionService;

    @Autowired
    public TransactionApi (TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/prepare")
    public TransactionResponseDto createTransaction (Authentication authentication)
            throws EmptyCartException, UnfinishedTransactionExistsException {

        logger.warn("entered API");
        String firebaseUid = SecurityUtil.getFirebaseUid(authentication);
        //LocalDateTime dateTime = LocalDateTime.now();
        long dateTime = System.currentTimeMillis();
        TransactionData transactionData = transactionService.createTransaction(firebaseUid, dateTime);
        return new TransactionResponseDto(transactionData);
    }

    @GetMapping("/{tid}")
    public TransactionResponseDto getTransactionDetailsByTid (@PathVariable Integer tid, Authentication authentication)
            throws TransactionNotFoundException, TransactionProductNotFound {
        String firebaseUid = SecurityUtil.getFirebaseUid(authentication);
        TransactionData transactionData = transactionService.getTransactionDetailsByTid(firebaseUid, tid);
        return new TransactionResponseDto(transactionData);
    }

    @PatchMapping("/{tid}/pay")
    public TransactionResponseDto updateTransactionStatus (@PathVariable Integer tid, Authentication authentication)
            throws TransactionNotFoundException, ProductNotFoundException, NotEnoughStockException, TransactionProductNotFound {
        logger.warn("entered processing API");
        String firebaseUid = SecurityUtil.getFirebaseUid(authentication);
        TransactionData transactionData = transactionService.updateTransactionStatusToProcessingByTid(firebaseUid, tid);
        return new TransactionResponseDto(transactionData);
    }

    @PatchMapping("/{tid}/finish")
    public TransactionResponseDto finishTransaction (@PathVariable Integer tid, Authentication authentication)
            throws TransactionNotFoundException, TransactionProductNotFound {
        String firebaseUid = SecurityUtil.getFirebaseUid(authentication);
        TransactionData transactionData = transactionService.finishTransaction(firebaseUid, tid);
        return new TransactionResponseDto(transactionData);
    }

}
