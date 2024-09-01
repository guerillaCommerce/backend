package com.github.service.usecase;

import com.github.common.exception.GlobalException;
import com.github.domain.Review;
import com.github.repository.ReviewRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class CreateReviewUseCase {
    private final ReviewRepository reviewRepository;

    public CreateReviewUseCase(final ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    private Review createReview(Review review) {
        try {
            return reviewRepository.save(review);
        } catch (Exception e) {
            throw new GlobalException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to save review");
        }
    }

    public Review exec(Review review) {
        return createReview(review);
    }
}
