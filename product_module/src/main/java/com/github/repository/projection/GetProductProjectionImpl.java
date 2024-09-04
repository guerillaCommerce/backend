package com.github.repository.projection;

import com.github.domain.type.AgeCategory;
import com.github.domain.type.GenderCategory;
import com.github.domain.type.ProductCategory;

import java.time.LocalDateTime;

public class GetProductProjectionImpl implements GetProductProjection {

    private final Long productId;
    private final Long sellerId;
    private final String shopName;
    private final String name;
    private final String content;
    private final Integer price;
    private final Integer leftAmount;
    private final ProductCategory productCategory;
    private final AgeCategory ageCategory;
    private final GenderCategory genderCategory;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final String thumbnailUrl;
    private final String imageUrls;
    private final String options;
    private final Double averageStarPoint;
    private final Boolean isDeleted;
    private final String shopImageUrl;

    public GetProductProjectionImpl(
            Long productId, Long sellerId, String shopName, String name,
            String content, Integer price, Integer leftAmount,
            ProductCategory productCategory, AgeCategory ageCategory,
            GenderCategory genderCategory, LocalDateTime createdAt,
            LocalDateTime updatedAt, String thumbnailUrl, String imageUrls,
            String options, Double averageStarPoint,
            Boolean isDeleted, String shopImageUrl
    ) {
        this.productId = productId;
        this.sellerId = sellerId;
        this.shopName = shopName;
        this.name = name;
        this.content = content;
        this.price = price;
        this.leftAmount = leftAmount;
        this.productCategory = productCategory;
        this.ageCategory = ageCategory;
        this.genderCategory = genderCategory;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.thumbnailUrl = thumbnailUrl;
        this.imageUrls = imageUrls;
        this.options = options;
        this.averageStarPoint = averageStarPoint;
        this.isDeleted = isDeleted;
        this.shopImageUrl = shopImageUrl;
    }

    @Override
    public Long getProductId() {
        return productId;
    }

    @Override
    public Long getSellerId() {
        return sellerId;
    }

    @Override
    public String getShopName() {
        return shopName;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public Integer getPrice() {
        return price;
    }

    @Override
    public Integer getLeftAmount() {
        return leftAmount;
    }

    @Override
    public ProductCategory getProductCategory() {
        return productCategory;
    }

    @Override
    public AgeCategory getAgeCategory() {
        return ageCategory;
    }

    @Override
    public GenderCategory getGenderCategory() {
        return genderCategory;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    @Override
    public String getImageUrls() {
        return imageUrls;
    }

    @Override
    public String getOptions() {
        return options;
    }

    @Override
    public Double getAverageStarPoint() {
        return averageStarPoint;
    }

    @Override
    public Boolean getIsDeleted() {
        return isDeleted;
    }

    @Override
    public String getShopImageUrl() {
        return shopImageUrl;
    }
}
