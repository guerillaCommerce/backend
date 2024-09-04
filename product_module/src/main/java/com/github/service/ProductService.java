package com.github.service;

import com.github.domain.Product;
import com.github.domain.type.AgeCategory;
import com.github.domain.type.GenderCategory;
import com.github.domain.type.ProductCategory;
import com.github.domain.type.SortType;
import com.github.dto.GetProductOutputDto;
import com.github.repository.projection.GetProductProjectionImpl;
import com.github.service.usecase.GetProductDetailUseCase;
import com.github.service.usecase.GetProductListUseCase;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final GetProductListUseCase getProductListUseCase;
    private final GetProductDetailUseCase getProductDetailUseCase;

    public ProductService(final GetProductListUseCase getProductListUseCase, final GetProductDetailUseCase getProductDetailUseCase) {
        this.getProductListUseCase = getProductListUseCase;
        this.getProductDetailUseCase = getProductDetailUseCase;
    }

    public List<GetProductOutputDto> getProductList(
            final int size,
            final int page,
            final SortType sort,
            final ProductCategory productCategory,
            final AgeCategory ageCategory,
            final GenderCategory genderCategory,
            final String search
    ) {
        Pageable pageable = PageRequest.of(page, size);
        List<GetProductProjectionImpl> result = getProductListUseCase.exec(
                pageable, sort, productCategory, ageCategory, genderCategory, search
        );
        System.out.println("444444444" + result.size());
        return GetProductOutputDto.from(result);
    }

    public GetProductOutputDto getProductDetail(final long productId) {
        Product result = getProductDetailUseCase.exec(productId);
        return GetProductOutputDto.from(result);
    }
}
