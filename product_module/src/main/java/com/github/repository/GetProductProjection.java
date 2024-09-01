package com.github.repository;

import java.time.LocalDateTime;

public interface GetProductProjection {

    long getProductId();

    long getSellerId();

    String getShopName();

    String getName();

    String getContent();

    int getPrice();

    int getLeftAmount();

    String getProductCategory();

    String getAgeCategory();

    String getGenderCategory();

    LocalDateTime getCreatedAt();

    LocalDateTime getUpdatedAt();

    String getThumbnailUrl();

    String getImageUrls();

    String getOptions();

    Double getAverageStarPoint();

    boolean isSeller();

    String getShopImageUrl();
}