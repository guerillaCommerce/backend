package com.github.commerce.web.controller.coupon;

import com.github.commerce.repository.user.UserDetailsImpl;
import com.github.commerce.service.coupon.UserCouponService;
import com.github.commerce.web.dto.coupon.UsersCouponResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/api/coupon/user")
public class UserCouponController {

    private final UserCouponService userCouponService;

    //본인의 쿠폰 목록 조회
    @GetMapping
    public ResponseEntity<List<UsersCouponResponseDto>> getMyCouponList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(userCouponService.getMyCouponList(userDetails.getUser().getId()));
    }

    //쿠폰 발급
    @PostMapping("/issue")
    public ResponseEntity<UsersCouponResponseDto> issueCoupon(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam("couponId") Long couponId) {
        return ResponseEntity.ok(userCouponService.issueUserCoupon(userDetails.getUser().getId(), couponId));
    }

    //쿠폰 사용 완료
    @PatchMapping("/used")
    public ResponseEntity<UsersCouponResponseDto> usedCoupon(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam("couponId") Long couponId) {
        return ResponseEntity.ok(userCouponService.usedUserCoupon(userDetails.getUser().getId(), couponId));
    }

}
