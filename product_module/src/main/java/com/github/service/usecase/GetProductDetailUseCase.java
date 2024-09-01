package com.github.service.usecase;

import com.github.common.exception.GlobalException;
import com.github.domain.Product;
import com.github.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GetProductDetailUseCase {
    private final ProductRepository productRepository;

    public GetProductDetailUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product getProductDetail(Long productId) {
        Product product = productRepository.findOneById(productId)
                .orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND, "Product not found for ID: " + productId));
        log.info("2222222222222" + product.getContent());
        return product;
    }

    public Product exec(long productId) {
        return this.getProductDetail(productId);
    }
}
