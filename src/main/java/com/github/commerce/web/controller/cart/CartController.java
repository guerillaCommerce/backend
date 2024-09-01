package com.github.commerce.web.controller.cart;

import com.github.commerce.repository.user.UserDetailsImpl;
import com.github.commerce.service.cart.CartService;
import com.github.commerce.web.dto.cart.GetCartDto;
import com.github.commerce.web.dto.cart.PostCartDto;
import com.github.commerce.web.dto.cart.PutCartDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/v1/api/cart")
@RestController
public class CartController {
    private final CartService cartService;


    /**
     * 장바구니 전체조회
     *
     * @param cursorId
     * @return
     */
    @GetMapping
    public ResponseEntity getAllCartWithCursor(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam(required = false) Long cursorId
    ) {
        Long userId = userDetails.getUser().getId();

        if (cursorId == null) {
            return ResponseEntity.ok(
                    cartService.getAllCarts(userId)
            );
        } else {
            return ResponseEntity.ok(
                    GetCartDto.Response.fromPage(
                            cartService.getAllCartWithCursor(userId, cursorId)
                    )
            );
        }
    }

    /**
     * 장바구니 상품추가
     *
     * @param request
     * @return
     */
    @PostMapping
    public ResponseEntity<List<String>> add(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody List<PostCartDto.PostCartRequest> request
    ) {
        Long userId = userDetails.getUser().getId();
        return ResponseEntity.ok(
                //PostCartDto.Response.from(cartService.addToCart(request, userId))
                cartService.addToCart(request, userId)
        );
    }

    /**
     * 장바구니 상품수정
     *
     * @param
     * @return
     */
    @PutMapping
    public ResponseEntity<List<String>> modify(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody List<PutCartDto.PutCartRequest> requestList
    ) {
        Long userId = userDetails.getUser().getId();
        return ResponseEntity.ok(cartService.modifyCart(requestList, userId));
    }

    /**
     * 장바구니 전체삭제
     *
     * @return
     */
    @DeleteMapping
    public ResponseEntity<String> deleteAll(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        Long userId = userDetails.getUser().getId();
        return ResponseEntity.ok(cartService.deleteAll(userId));
    }

    /**
     * 장바구니 개별삭제
     *
     * @return
     */
    @DeleteMapping("/{cartId}")
    public ResponseEntity<String> deleteOne(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long cartId
    ) {
        Long userId = userDetails.getUser().getId();
        return ResponseEntity.ok(cartService.deleteOne(cartId, userId));
    }

}
