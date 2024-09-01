package com.github.controller;

import com.github.dto.PostReviewInputDto;
import com.github.dto.PostReviewOutputDto;
import com.github.service.ReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequestMapping("/v1/api/review")
@RestController
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(final ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PostReviewOutputDto> createReview(
            @ModelAttribute PostReviewInputDto inputDto,
            @RequestParam(required = false) MultipartFile multipartFile
    ) {
        return ResponseEntity.ok(
                reviewService.createReview(inputDto, multipartFile)
        );
    }


    /**
     * 리뷰 삭제
     *
     * @param reviewId
     * @return
     */
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> delete(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long reviewId
    ) {
        Long userId = userDetails.getUser().getId();
        return ResponseEntity.ok(
                reviewService.deleteReview(reviewId, userId)
        );
    }
}
