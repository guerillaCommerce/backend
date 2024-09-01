package com.github.controller;

import com.github.common.response.Response;
import com.github.dto.GetProductInputDto;
import com.github.dto.GetProductOutputDto;
import com.github.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("v1/api/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public Response<List<GetProductOutputDto>> getProductList(
            GetProductInputDto inputDto
    ) {
        return Response.success(
                productService.getProductList(
                        inputDto.getSize(),
                        inputDto.getPage(),
                        inputDto.getSort(),
                        inputDto.getProductCategory(),
                        inputDto.getAgeCategory(),
                        inputDto.getGenderCategory(),
                        inputDto.getSearch()
                )
        );
    }

    @GetMapping("/{productId}")
    public Response<GetProductOutputDto> getProduct(
            @PathVariable Long productId
    ) {
        return Response.success(productService.getProductDetail(productId));
    }
}
