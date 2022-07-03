package com.fsse2203.project_backend.service.impl;

import com.fsse2203.project_backend.data.product.ProductDetailsData;
import com.fsse2203.project_backend.data.product.GetAllProductsData;
import com.fsse2203.project_backend.data.product.ProductDetailsRequestData;
import com.fsse2203.project_backend.data.product.entity.ProductEntity;
import com.fsse2203.project_backend.data.transaction.data.entity.TransactionProductEntity;
import com.fsse2203.project_backend.exception.NotEnoughStockException;
import com.fsse2203.project_backend.exception.ProductNotFoundException;
import com.fsse2203.project_backend.reposotory.ProductRepository;
import com.fsse2203.project_backend.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl (ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductDetailsData createProduct (ProductDetailsRequestData productDetailsRequestData) {

        ProductEntity createdEntity = productRepository.save(new ProductEntity(productDetailsRequestData));
        return new ProductDetailsData(createdEntity);
    }

    @Override
    public List<GetAllProductsData> getAllProducts () {

        List<GetAllProductsData> productsDataList = new ArrayList<>();

        for (ProductEntity entity : productRepository.findAll()) {
            productsDataList.add(new GetAllProductsData(entity));
        }
        return productsDataList;
    }

    @Override
    public ProductDetailsData getAProductById (Integer productId) throws ProductNotFoundException {
        ProductEntity entity = findProductEntity(productId);
        return new ProductDetailsData(entity);
    }

    @Override
    public ProductEntity findProductEntity(Integer productId) throws ProductNotFoundException {
        ProductEntity entity = productRepository.findById(productId).orElse(null);
        if (entity == null) {
            throw new ProductNotFoundException();
        }
        return entity;
    }

    @Override
    public void reduceStock (List<TransactionProductEntity> transactionProductEntities)
            throws ProductNotFoundException, NotEnoughStockException {

        logger.warn("entered reduce stock method");
        for (TransactionProductEntity tranProduct : transactionProductEntities) {
            ProductEntity productEntity = productRepository.findById(tranProduct.getPid()).orElse(null);
            if (productEntity==null) {
                logger.warn("ProductNotFoundException exception");
                throw new ProductNotFoundException();
            }
            if (productEntity.getStock() < tranProduct.getTpQuantity()){
                logger.warn("NotEnoughStockException exception");
                throw new NotEnoughStockException();
            }
            Integer updatedStock = productEntity.getStock() - tranProduct.getTpQuantity();
            productEntity.setStock(updatedStock);
            productRepository.save(productEntity);
        }
    }
}
