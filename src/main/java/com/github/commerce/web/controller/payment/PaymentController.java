package com.github.commerce.web.controller.payment;

import com.github.commerce.repository.user.UserDetailsImpl;
import com.github.commerce.service.payment.PaymentService;
import com.github.commerce.web.dto.payment.PurchaseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/v1/api/payments")
@RestController
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/purchase")
    public ResponseEntity<PurchaseDto.PurchaseResponse> purchase(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody PurchaseDto.PurchaseRequest request) {

        Long userId = userDetails.getUser().getId();

        return ResponseEntity.ok(PurchaseDto.PurchaseResponse.from(paymentService.purchaseOrder(userId, request)));

    }
}
