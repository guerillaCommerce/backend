package com.github.commerce.web.controller.product;

import com.github.commerce.repository.user.UserDetailsImpl;
import com.github.commerce.service.product.ProductService;
import com.github.commerce.service.review.ReviewService;
import com.github.commerce.web.advice.custom.ResponseDto;
import com.github.commerce.web.dto.product.GetProductDto;
import com.github.commerce.web.dto.product.ProductDto;
import com.github.commerce.web.dto.review.ReviewDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("v1/api/product")
public class ProductController {

    private final ProductService productService;
    private final ReviewService reviewService;

    @GetMapping("/search") //  ?pageNumber=1&searchWord=반바지
    public ResponseEntity<List<GetProductDto>> searchProduct(
            @RequestParam(name = "size", required = false, defaultValue = "15") Integer size,
            @RequestParam(name = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
            @RequestParam(name = "searchWord", required = false, defaultValue = "") String searchWord,
            @RequestParam(name = "ageCategory", required = false, defaultValue = "") String ageCategory,
            @RequestParam(name = "genderCategory", required = false, defaultValue = "") String genderCategory,
            @RequestParam(name = "sortBy", required = false, defaultValue = "id") String sortBy
    ) {
        return ResponseEntity.ok(productService.searchProducts(size, pageNumber, searchWord, ageCategory, genderCategory, sortBy));
    }


    @GetMapping
    public ResponseEntity<List<GetProductDto>> getProducts(
            @RequestParam(name = "size", required = false, defaultValue = "15") Integer size,
            @RequestParam(name = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
            @RequestParam(name = "ageCategory", required = false, defaultValue = "") String ageCategory,
            @RequestParam(name = "genderCategory", required = false, defaultValue = "") String genderCategory,
            @RequestParam(name = "sortBy", required = false, defaultValue = "id") String sortBy
    ) {
        return ResponseEntity.ok(productService.getProductList(size, pageNumber, ageCategory, genderCategory, sortBy));
    }

    @GetMapping("/category/{productCategory}")
    public ResponseEntity<List<GetProductDto>> getProductsByCategory(

            @PathVariable String productCategory, //필수
            @RequestParam(name = "size", required = false, defaultValue = "15") Integer size,
            @RequestParam(name = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
            @RequestParam(name = "ageCategory", required = false, defaultValue = "") String ageCategory,
            @RequestParam(name = "genderCategory", required = false, defaultValue = "") String genderCategory,
            @RequestParam(name = "sortBy", required = false, defaultValue = "id") String sortBy
    ) {
        return ResponseEntity.ok(productService.getProductsByCategory(size, pageNumber, productCategory, ageCategory, genderCategory, sortBy));
    }

    @GetMapping("/detail/{productId}")
    public ResponseEntity<ProductDto> getProduct(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long productId
    ) {
        Long userId = userDetails != null ? userDetails.getUser().getId() : null;
        String userName = userDetails != null ? userDetails.getUser().getUserName() : null;
        return ResponseEntity.ok(productService.getOneProduct(productId, userId, userName));
    }

    // 판매자가 상품 등록
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductDto> createProduct(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam(name = "productRequest") String productRequest,//JSON.stringify()
            @RequestParam(required = false) List<MultipartFile> imageFiles) {
        Long profileId = (userDetails != null) ? userDetails.getUser().getId() : null;
        return ResponseEntity.ok(productService.createProductItem(productRequest, imageFiles, profileId));
    }

    // 상품 수정
    @PatchMapping(value = "/{productId}")
    public ResponseDto<?> updateProduct(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                        @PathVariable("productId") Long productId,
                                        @RequestParam(name = "productRequest") String productRequest,
                                        @RequestParam(required = false) MultipartFile thumbnailFile,
                                        @RequestParam(required = false) List<MultipartFile> imageFiles) {
        Long profileId = (userDetails != null) ? userDetails.getUser().getId() : null;
        ProductDto updatedProduct = productService.updateProductById(productId, profileId, productRequest, thumbnailFile, imageFiles);
        return ResponseDto.success(updatedProduct);
    }


    // 상품 삭제
    @DeleteMapping("/{productId}")
    public ResponseDto<String> deleteProduct(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable("productId") Long productId) {
        // 유저 존재 확인
        Long profileId = (userDetails != null) ? userDetails.getUser().getId() : null;
        productService.deleteProductByProductId(productId, profileId);
        return ResponseDto.success(productId + "번 상품이 삭제 되었습니다.");
    }


    /**
     * 로그인 필요 없음
     * 상품 리뷰 조회
     *
     * @param productId
     * @param cursorId
     * @return
     */
    @GetMapping("/review/{productId}")
    public ResponseEntity<List<ReviewDto>> get(
            @PathVariable Long productId,
            @RequestParam(defaultValue = "0") Long cursorId
    ) {
        return ResponseEntity.ok(reviewService.getReviews(productId, cursorId));
    }

}
