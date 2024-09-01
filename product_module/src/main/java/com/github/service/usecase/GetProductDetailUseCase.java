package com.github.service.usecase;

import com.github.common.exception.GlobalException;
import com.github.domain.Product;
import com.github.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class GetProductDetailUseCase {
    private final ProductRepository productRepository;

    public GetProductDetailUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product getProductDetail(Long productId) {
        return productRepository.findOneById(productId)
                .orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND, "Product not found for ID: " + productId));
    }

    public Product exec(long productId) {
        return this.getProductDetail(productId);
    }
}
