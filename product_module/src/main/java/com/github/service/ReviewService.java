package com.github.service;

import com.github.domain.Product;
import com.github.domain.Review;
import com.github.dto.PostReviewInputDto;
import com.github.dto.PostReviewOutputDto;
import com.github.service.usecase.CreateReviewUseCase;
import com.github.service.usecase.GetProductDetailUseCase;
import com.github.usecase.CheckItExistsUseCase;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ReviewService {
    private final CreateReviewUseCase createReviewUseCase;
    private final GetProductDetailUseCase getProductDetailUseCase;
    private final CheckItExistsUseCase checkItExistsUseCase;

    public ReviewService(final CreateReviewUseCase createReviewUseCase, final GetProductDetailUseCase getProductDetailUseCase, CheckItExistsUseCase checkItExistsUseCase) {
        this.createReviewUseCase = createReviewUseCase;
        this.getProductDetailUseCase = getProductDetailUseCase;
        this.checkItExistsUseCase = checkItExistsUseCase;
    }

    public PostReviewOutputDto createReview(PostReviewInputDto inputDto, MultipartFile multipartFile) {
        Product product = getProductDetailUseCase.exec(inputDto.getProductId());
        checkItExistsUseCase.exec(product, "productId에 해당하는 상품이 없습니다.");
        Review review = PostReviewInputDto.toEntity(product, inputDto);
        Review result = createReviewUseCase.exec(review);
        return PostReviewOutputDto.from(result);
    }
}
