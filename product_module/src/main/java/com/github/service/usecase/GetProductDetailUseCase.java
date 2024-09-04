package com.github.service.usecase;

import com.github.common.exception.GlobalException;
import com.github.domain.Product;
import com.github.repository.ProductRepositoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GetProductDetailUseCase {
    private final ProductRepositoryImpl productRepository;

    public GetProductDetailUseCase(ProductRepositoryImpl productRepository) {
        this.productRepository = productRepository;
    }

    public Product getProductDetail(final long productId) {
        return productRepository.getProductDetailWithReviewListByProductId(productId)
                .orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND, "Product not found for ID: " + productId));
    }

    public Product exec(long productId) {
        return this.getProductDetail(productId);
    }
}
