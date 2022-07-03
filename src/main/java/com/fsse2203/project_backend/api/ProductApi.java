package com.fsse2203.project_backend.api;


import com.fsse2203.project_backend.config.EnvConfig;
import com.fsse2203.project_backend.data.product.ProductDetailsData;
import com.fsse2203.project_backend.data.product.GetAllProductsData;
import com.fsse2203.project_backend.data.product.ProductDetailsRequestData;
import com.fsse2203.project_backend.data.product.dto.CreateProductRequestDto;
import com.fsse2203.project_backend.data.product.dto.CreateProductResponseDto;
import com.fsse2203.project_backend.data.product.dto.GetAProductResponseDto;
import com.fsse2203.project_backend.data.product.dto.GetAllProductsResponseDto;
import com.fsse2203.project_backend.exception.ProductNotFoundException;
import com.fsse2203.project_backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = {EnvConfig.devBaseUrl,
        EnvConfig.prodBaseUrl},
        maxAge = 3600)
@RestController
public class ProductApi {

    private final ProductService productService;

    @Autowired
    public ProductApi (ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("product/create")
    public CreateProductResponseDto createProduct (@RequestBody CreateProductRequestDto createProductRequestDto) {

        ProductDetailsData productDetailsData =
        productService.createProduct(new ProductDetailsRequestData(createProductRequestDto));

        return new CreateProductResponseDto(productDetailsData);
    }

    @GetMapping("public/product")
    public List<GetAllProductsResponseDto> getAllProducts () {
        List<GetAllProductsData> productDataList = productService.getAllProducts();
        List<GetAllProductsResponseDto> productDtoList = new ArrayList<>();
        for (GetAllProductsData data : productDataList) {
            productDtoList.add(new GetAllProductsResponseDto(data));
        }

        return productDtoList;
    }

    @GetMapping("/public/product/{id}")
    public GetAProductResponseDto getAProductById (@PathVariable("id") Integer productId)
            throws ProductNotFoundException {

        ProductDetailsData productData = productService.getAProductById(productId);
        return new GetAProductResponseDto(productData);
    }

}
