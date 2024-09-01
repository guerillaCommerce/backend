package com.github.commerce.web.dto.payment;

import lombok.*;
import java.time.LocalDateTime;


public class ChargeDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ChargeRequest {

        private Long payMoney;
        private Long paymentAmount;

    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ChargeResponse {

        private Long payMoney;

        private Long paymentAmount;

        private LocalDateTime chargeDate;

        private String status;

        public static ChargeDto.ChargeResponse from(ChargeHistoryDto chargeHistoryDto){
            return ChargeResponse.builder()
                    .payMoney(chargeHistoryDto.getPayMoney())
                    .paymentAmount(chargeHistoryDto.getPaymentAmount())
                    .chargeDate(chargeHistoryDto.getChargeDate())
                    .status(chargeHistoryDto.getStatus())
                    .build();
        }


    }

}
