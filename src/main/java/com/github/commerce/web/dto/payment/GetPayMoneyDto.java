package com.github.commerce.web.dto.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class GetPayMoneyDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class GetPayMoneyResponse {

        private Long usedPayMoney;

        private Long chargePayMoney;

        private Long point;

        private Long payMoneyBalance;

        private LocalDateTime createAt;

        public static GetPayMoneyResponse from(PayMoneyDto payMoneyDto) {
            return GetPayMoneyResponse.builder()
                    .usedPayMoney(payMoneyDto.getUsedPayMoney())
                    .chargePayMoney(payMoneyDto.getChargePayMoneyTotal())
                    .point(payMoneyDto.getPointBalance())
                    .payMoneyBalance(payMoneyDto.getPayMoneyBalance())
                    .createAt(payMoneyDto.getCreatedAt())
                    .build();
        }

    }
}
