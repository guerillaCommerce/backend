package com.github.service.usecase;

import com.github.dto.type.AgeCategory;
import com.github.dto.type.GenderCategory;
import com.github.dto.type.ProductCategory;
import com.github.dto.type.SortType;
import com.github.repository.GetProductProjection;
import com.github.repository.ProductRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

@Component
public class GetProductListUseCase {
    private final ProductRepository productRepository;

    public GetProductListUseCase(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    private List<GetProductProjection> getProductListBySearchToken(
            final String searchToken,
            final Pageable pageable,
            final SortType sort,
            final ProductCategory productCategory,
            final AgeCategory ageCategory,
            final GenderCategory genderCategory
    ) {
        String productCategoryName = productCategory == ProductCategory.ALL ? null : productCategory.name();
        String ageCategoryName = ageCategory == AgeCategory.ALL ? null : ageCategory.name();
        String genderCategoryName = genderCategory == GenderCategory.ALL ? null : genderCategory.name();
        switch (sort) {
            case SortType.PRICE:
                return productRepository.searchProductSortByPrice(
                        searchToken,
                        productCategoryName,
                        ageCategoryName,
                        genderCategoryName,
                        pageable
                );
            case SortType.LATEST:
                return productRepository.searchProductSortByCreatedAt(
                        searchToken,
                        productCategoryName,
                        ageCategoryName,
                        genderCategoryName,
                        pageable
                );
            default:
                return productRepository.searchProductSortById(
                        searchToken,
                        productCategoryName,
                        ageCategoryName,
                        genderCategoryName,
                        pageable
                );
        }
    }

    private List<GetProductProjection> getProductListByCategory(
            final Pageable pageable,
            final SortType sort,
            final ProductCategory productCategory,
            final AgeCategory ageCategory,
            final GenderCategory genderCategory
    ) {
        String productCategoryName = productCategory == ProductCategory.ALL ? null : productCategory.name();
        String ageCategoryName = ageCategory == AgeCategory.ALL ? null : ageCategory.name();
        String genderCategoryName = genderCategory == GenderCategory.ALL ? null : genderCategory.name();
        switch (sort) {
            case SortType.PRICE:
                return productRepository.findAllSortByPrice(
                        productCategoryName,
                        ageCategoryName,
                        genderCategoryName,
                        pageable
                );
            case SortType.LATEST:
                return productRepository.findAllSortByCreatedAt(
                        productCategoryName,
                        ageCategoryName,
                        genderCategoryName,
                        pageable
                );
            default:
                return productRepository.findAllSortById(
                        productCategoryName,
                        ageCategoryName,
                        genderCategoryName,
                        pageable
                );
        }
    }

    public List<GetProductProjection> exec(
            final Pageable pageable,
            final SortType sort,
            final ProductCategory productCategory,
            final AgeCategory ageCategory,
            final GenderCategory genderCategory,
            final String search
    ) {
        if (StringUtils.hasText(search)) {
            String searchToken = "%" + search + "%";
            return this.getProductListBySearchToken(
                    searchToken,
                    pageable,
                    sort,
                    productCategory,
                    ageCategory,
                    genderCategory
            );
        } else {
            return this.getProductListByCategory(
                    pageable,
                    sort,
                    productCategory,
                    ageCategory,
                    genderCategory
            );
        }
    }
}