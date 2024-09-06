package com.github.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.domain.Product;
import com.github.repository.projection.GetProductProjectionImpl;
import lombok.Builder;
import lombok.Getter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
public class GetProductOutputDto {
    private long productId;
    private long sellerId;
    private String shopName;
    private String shopImageUrl;
    private String name;
    private String content;
    private int price;
    private int leftAmount;
    private String productCategory;
    private String ageCategory;
    private String genderCategory;
    private String thumbnailUrl;
    private List<String> imageUrls;
    private String options;
    private Double averageStarPoint;
    private boolean isSeller;
    private List<GetReviewOutputDto> reviews;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    public static GetProductOutputDto from(GetProductProjectionImpl projection) {

        return GetProductOutputDto.builder()
                .productId(projection.getProductId())
                .sellerId(projection.getSellerId())
                .shopName(projection.getShopName())
                .name(projection.getName())
                .content(projection.getContent())
                .price(projection.getPrice())
                .leftAmount(projection.getLeftAmount())
                .productCategory(projection.getProductCategory().name())
                .ageCategory(projection.getAgeCategory().name())
                .genderCategory(projection.getGenderCategory().name())
                .createdAt(projection.getCreatedAt())
                .updatedAt(projection.getUpdatedAt())
                .thumbnailUrl(projection.getThumbnailUrl())
                .imageUrls(parseImageUrls(projection.getImageUrls()))
                .options(projection.getOptions())
                .averageStarPoint(projection.getAverageStarPoint())
                .shopImageUrl(projection.getShopImageUrl())
                .build();
    }

    public static List<GetProductOutputDto> from(List<GetProductProjectionImpl> projections) {
        List<GetProductOutputDto> dtoList = new ArrayList<>();
        for (GetProductProjectionImpl projection : projections) {
            dtoList.add(from(projection));
        }
        return dtoList;
    }

    public static GetProductOutputDto from(Product product) {
        return GetProductOutputDto.builder()
                .productId(product.getId())
                .sellerId(product.getSeller().getId())
                .shopName(product.getSeller().getShopName())
                .name(product.getName())
                .content(product.getContent())
                .price(product.getPrice())
                .leftAmount(product.getLeftAmount())
                .productCategory(product.getProductCategory().name())
                .ageCategory(product.getAgeCategory().name())
                .genderCategory(product.getGenderCategory().name())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .thumbnailUrl(product.getThumbnailUrl())
                .imageUrls(parseImageUrls(product.getImageUrls()))
                .options(product.getOptions())
                .averageStarPoint(product.getAverageStarPoint())
                .shopImageUrl(product.getSeller().getShopImageUrl())
                .reviews(GetReviewOutputDto.from(product.getReviews()))
                .build();
    }

    private static List<String> parseImageUrls(String imageUrlsJson) {
        ObjectMapper objectMapper = new ObjectMapper();
        if (imageUrlsJson == null) {
            return new ArrayList<>();
        }
        try {
            return objectMapper.readValue(imageUrlsJson, new TypeReference<List<String>>() {
            });
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }
}