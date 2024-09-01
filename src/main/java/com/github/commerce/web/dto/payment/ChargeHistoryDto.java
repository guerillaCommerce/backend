package com.github.commerce.web.dto.payment;

import com.github.commerce.entity.ChargeHistory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChargeHistoryDto {

    private Long payMoney;

    private Long paymentAmount;

    private LocalDateTime chargeDate;
    
    private String status;

    public static ChargeHistoryDto from(ChargeHistory chargeHistory) {
        return ChargeHistoryDto.builder()
                .payMoney(chargeHistory.getPayMoney())
                .paymentAmount(chargeHistory.getPaymentAmount())
                .chargeDate(chargeHistory.getChargeDate())
                .status(PaymentStatusEnum.getByCode(chargeHistory.getStatus() != null ? chargeHistory.getStatus() : 1))
                .build();
    }
}
