package com.github.repository.projection;

import com.github.domain.type.AgeCategory;
import com.github.domain.type.GenderCategory;
import com.github.domain.type.ProductCategory;

import java.time.LocalDateTime;

public interface GetProductProjection {
    Long getProductId();

    Long getSellerId();

    String getShopName();

    String getName();

    String getContent();

    Integer getPrice();

    Integer getLeftAmount();

    ProductCategory getProductCategory();

    AgeCategory getAgeCategory();

    GenderCategory getGenderCategory();

    LocalDateTime getCreatedAt();

    LocalDateTime getUpdatedAt();

    String getThumbnailUrl();

    String getImageUrls();

    String getOptions();

    Double getAverageStarPoint();

    Boolean getIsDeleted();

    String getShopImageUrl();
}
