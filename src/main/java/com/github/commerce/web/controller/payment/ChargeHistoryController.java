package com.github.commerce.web.controller.payment;

import com.github.commerce.repository.user.UserDetailsImpl;
import com.github.commerce.service.payment.ChargeHistoryService;
import com.github.commerce.web.dto.payment.ChargeDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/charge")
public class ChargeHistoryController {

    private final ChargeHistoryService chargeHistoryService;

    @PostMapping("/pay-moneys")
    public ResponseEntity<ChargeDto.ChargeResponse> charge(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody @Valid ChargeDto.ChargeRequest request) {

        Long userId = userDetails.getUser().getId();
        return ResponseEntity.ok(chargeHistoryService.chargePayMoney(userId, request));
    }
}
