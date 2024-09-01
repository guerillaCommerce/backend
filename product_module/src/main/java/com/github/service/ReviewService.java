package com.github.service;

import com.github.domain.Product;
import com.github.domain.Review;
import com.github.dto.PostReviewInputDto;
import com.github.dto.PostReviewOutputDto;
import com.github.service.usecase.CreateFileNameUseCase;
import com.github.service.usecase.CreateReviewUseCase;
import com.github.service.usecase.GetProductDetailUseCase;
import com.github.service.usecase.UploadImageUseCase;
import com.github.usecase.CheckItExistsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final CreateReviewUseCase createReviewUseCase;
    private final GetProductDetailUseCase getProductDetailUseCase;
    private final CheckItExistsUseCase checkItExistsUseCase;
    private final CreateFileNameUseCase createFileNameUseCase;
    private final UploadImageUseCase uploadImageUseCase;


    public PostReviewOutputDto createReview(PostReviewInputDto inputDto, MultipartFile multipartFile) {
        Product product = getProductDetailUseCase.exec(inputDto.getProductId());
        checkItExistsUseCase.exec(product, "productId에 해당하는 상품이 없습니다.");
        Review review = PostReviewInputDto.toEntity(product, inputDto);
        
        String fileName = createFileNameUseCase.exec(multipartFile);
        String imageUrl = uploadImageUseCase.exec(multipartFile, fileName);
        review.setImageUrl(imageUrl);

        Review result = createReviewUseCase.exec(review);

        return PostReviewOutputDto.from(result);
    }
}
