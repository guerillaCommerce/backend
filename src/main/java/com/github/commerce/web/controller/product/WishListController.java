package com.github.commerce.web.controller.product;

import com.github.commerce.entity.Product;
import com.github.commerce.entity.User;
import com.github.commerce.repository.product.ProductRepository;
import com.github.commerce.repository.user.SellerRepository;
import com.github.commerce.repository.user.UserDetailsImpl;
import com.github.commerce.service.product.WishlistService;
import com.github.commerce.service.product.util.ValidateProductMethod;
import com.github.commerce.service.user.exception.UserErrorCode;
import com.github.commerce.service.user.exception.UserException;
import com.github.commerce.web.advice.custom.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("v1/api/wishlist")
public class WishListController {
    private final WishlistService wishlistService;
    private final ValidateProductMethod validateProductMethod;
    private final ProductRepository productRepository;
    private final SellerRepository sellerRepository;

    @PostMapping("/add/{productId}")
    public ResponseDto<String> addWishlist(@PathVariable(value = "productId", required = false) Long productId,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long profileId = userDetails.getUser().getId();
        User validateProfileId = validateProductMethod.validateUser(profileId);
        if (sellerRepository.existsByUsersId(validateProfileId.getId())) {
            throw new UserException(UserErrorCode.NOT_USER);
        }
        Product validateProduct = validateProductMethod.validateProduct(productId);
        try {
            boolean addedToWishlist = wishlistService.addWishlist(validateProfileId, validateProduct);
            if (addedToWishlist) {
                return ResponseDto.success("찜 목록에 상품이 추가 되었습니다.");
            } else {
                return ResponseDto.fail("찜 목록에 상품이 포함되어 있습니다.");
            }
        } catch (Exception e) {
            return ResponseDto.fail("판매자는 찜 등록 및 삭제를 할 수 없습니다.");
        }
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseDto<String> removeWishlist(@PathVariable(value = "productId", required = false) Long productId,
                                              @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long profileId = userDetails.getUser().getId();
        User validateProfileId = validateProductMethod.validateUser(profileId);
        if (sellerRepository.existsByUsersId(validateProfileId.getId())) {
            throw new UserException(UserErrorCode.NOT_USER);
        }
        Product validateProduct = validateProductMethod.validateProduct(productId);
        try {
            boolean removedFromWishlist = wishlistService.removeWishlist(validateProfileId, validateProduct);
            if (removedFromWishlist) {
                return ResponseDto.success("찜 목록에서 제거되었습니다.");
            } else {
                return ResponseDto.fail("찜 목록에 포함되지 않은 상품입니다.");
            }
        } catch (Exception e) {
            return ResponseDto.fail("판매자는 찜 등록 및 삭제를 할 수 없습니다.");
        }
    }

    @GetMapping
    public ResponseDto<List<Map<String, Object>>> getWishlist(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long profileId = userDetails.getUser().getId();
        User validateProfileId = validateProductMethod.validateUser(profileId);
        if (sellerRepository.existsByUsersId(validateProfileId.getId())) {
            throw new UserException(UserErrorCode.NOT_USER);
        }
        List<Map<String, Object>> getProducts = wishlistService.getWishlist(validateProfileId);
        return ResponseDto.success(getProducts);
    }

}