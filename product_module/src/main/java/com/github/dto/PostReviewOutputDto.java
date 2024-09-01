package com.github.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.domain.Review;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class PostReviewOutputDto {
    private long reviewId;
    private long productId;
    private String author;
    private String content;
    private int starPoint;
    private String imageUrl;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    public static PostReviewOutputDto from(Review review) {
        return PostReviewOutputDto.builder()
                .reviewId(review.getId())
                .productId(review.getProduct().getId())
                .author(review.getAuthor())
                .content(review.getContent())
                .starPoint(review.getStarPoint())
                .imageUrl(review.getImageUrl())
                .createdAt(review.getCreatedAt())
                .build();
    }

}
