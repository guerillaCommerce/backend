package com.github.service.usecase;

import com.github.domain.type.AgeCategory;
import com.github.domain.type.GenderCategory;
import com.github.domain.type.ProductCategory;
import com.github.domain.type.SortType;
import com.github.repository.ProductRepositoryImpl;
import com.github.repository.projection.GetProductProjectionImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetProductListUseCase {
    private final ProductRepositoryImpl productRepository;

    public GetProductListUseCase(final ProductRepositoryImpl productRepository) {
        this.productRepository = productRepository;
    }

    private List<GetProductProjectionImpl> getProductListBySearchToken(
            final String searchToken,
            final Pageable pageable,
            final SortType sort,
            final ProductCategory inputProductCategory,
            final AgeCategory inputAgeCategory,
            final GenderCategory inputGenderCategory
    ) {
        ProductCategory productCategory = inputProductCategory == ProductCategory.ALL ? null : inputProductCategory;
        AgeCategory ageCategory = inputAgeCategory == AgeCategory.ALL ? null : inputAgeCategory;
        GenderCategory genderCategory = inputGenderCategory == GenderCategory.ALL ? null : inputGenderCategory;

        String sortBy;
        switch (sort) {
            case SortType.PRICE:
                sortBy = "price";
                break;
            case SortType.LATEST:
                sortBy = "createdAt";
                break;
            default:
                sortBy = "id";
                break;
        }

        return productRepository.getProductList(
                searchToken,
                productCategory,
                ageCategory,
                genderCategory,
                sortBy,
                pageable
        );
    }

    private List<GetProductProjectionImpl> getProductListByCategory(
            final Pageable pageable,
            final SortType sort,
            final ProductCategory inputProductCategory,
            final AgeCategory inputAgeCategory,
            final GenderCategory inputGenderCategory
    ) {
        ProductCategory productCategory = inputProductCategory == ProductCategory.ALL ? null : inputProductCategory;
        AgeCategory ageCategory = inputAgeCategory == AgeCategory.ALL ? null : inputAgeCategory;
        GenderCategory genderCategory = inputGenderCategory == GenderCategory.ALL ? null : inputGenderCategory;

        String sortBy;
        switch (sort) {
            case SortType.PRICE:
                sortBy = "price";
                break;
            case SortType.LATEST:
                sortBy = "createdAt";
                break;
            default:
                sortBy = "id";
                break;
        }

        return productRepository.getProductList(
                null,
                productCategory,
                ageCategory,
                genderCategory,
                sortBy,
                pageable
        );
    }

    public List<GetProductProjectionImpl> exec(
            final Pageable pageable,
            final SortType sort,
            final ProductCategory inputProductCategory,
            final AgeCategory inputAgeCategory,
            final GenderCategory inputGenderCategory,
            final String search
    ) {
        if (search != null && !search.trim().isEmpty()) {
            String searchToken = "%" + search + "%";
            return this.getProductListBySearchToken(
                    searchToken,
                    pageable,
                    sort,
                    inputProductCategory,
                    inputAgeCategory,
                    inputGenderCategory
            );
        } else {
            return this.getProductListByCategory(
                    pageable,
                    sort,
                    inputProductCategory,
                    inputAgeCategory,
                    inputGenderCategory
            );
        }
    }
}
