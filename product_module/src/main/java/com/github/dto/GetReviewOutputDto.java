package com.github.dto;

import com.github.domain.Review;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
public class GetReviewOutputDto {
    private long reviewId;
    private String author;
    private String content;
    private int starPoint;
    private String imageUrl;

    public static GetReviewOutputDto from(Review review) {
        return GetReviewOutputDto.builder()
                .reviewId(review.getId())
                .author(review.getAuthor())
                .content(review.getContent())
                .starPoint(review.getStarPoint())
                .imageUrl(review.getImageUrl())
                .build();
    }

    public static List<GetReviewOutputDto> from(List<Review> reviews) {
        List<GetReviewOutputDto> dtoList = new ArrayList<>();
        for (Review review : reviews) {
            dtoList.add(from(review));
        }
        return dtoList;
    }
}
