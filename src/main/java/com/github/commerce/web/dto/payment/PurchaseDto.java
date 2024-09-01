package com.github.commerce.web.dto.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class PurchaseDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PurchaseRequest {

        private List<Long> orderIdList;

        private Long couponId;

        private Long totalPrice;

        private Boolean isUsePoint;

        private String paymentMethod;

    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PurchaseResponse {

        private Long paymentId;

        private Long payMoneyId;

        private Long payMoneyAmount;

        private Long payMoneyBalance;

        private LocalDateTime createdAt;

        private String paymentMethod;

        private String status;

        public static PurchaseResponse from(PaymentDto paymentDto) {
            return PurchaseResponse.builder()
                    .paymentId(paymentDto.getId())
                    .payMoneyId(paymentDto.getPayMoneyId())
                    .payMoneyAmount(paymentDto.getPayMoneyAmount())
                    .payMoneyBalance(paymentDto.getPayMoneyBalance())
                    .paymentMethod(paymentDto.getPaymentMethod())
                    .status(paymentDto.getStatus())
                    .createdAt(paymentDto.getCreatedAt())
                    .build();
        }

    }

}
