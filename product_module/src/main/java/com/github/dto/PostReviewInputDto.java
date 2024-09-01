package com.github.dto;

import com.github.domain.Product;
import com.github.domain.Review;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class PostReviewInputDto {
    private long productId;
    private String author;
    private String content;
    private int starPoint;

    public static Review toEntity(Product product, PostReviewInputDto dto) {
        return Review.builder()
                .product(product)
                .author(dto.getAuthor())
                .content(dto.getContent())
                .starPoint((short) dto.getStarPoint())
                .createdAt(LocalDateTime.now())
                .build();
    }
}
