package com.fsse2203.project_backend.service;

import com.fsse2203.project_backend.data.product.ProductDetailsData;
import com.fsse2203.project_backend.data.product.GetAllProductsData;
import com.fsse2203.project_backend.data.product.ProductDetailsRequestData;
import com.fsse2203.project_backend.data.product.entity.ProductEntity;
import com.fsse2203.project_backend.data.transaction.data.entity.TransactionProductEntity;
import com.fsse2203.project_backend.exception.NotEnoughStockException;
import com.fsse2203.project_backend.exception.ProductNotFoundException;

import java.util.List;

public interface ProductService {
    ProductDetailsData createProduct (ProductDetailsRequestData productDetailsRequestData);
    List<GetAllProductsData> getAllProducts ();
    ProductDetailsData getAProductById (Integer productid) throws ProductNotFoundException;
    ProductEntity findProductEntity(Integer productid) throws ProductNotFoundException;
    void reduceStock (List<TransactionProductEntity> transactionProductEntities)
            throws ProductNotFoundException, NotEnoughStockException;
}
