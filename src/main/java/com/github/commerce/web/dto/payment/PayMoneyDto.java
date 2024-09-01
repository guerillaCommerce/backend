package com.github.commerce.web.dto.payment;

import com.github.commerce.entity.ChargeHistory;
import com.github.commerce.entity.PayMoney;
import com.github.commerce.entity.Payment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PayMoneyDto {

    private Long paymentId;

    private Long chargeHistoryId;

    private Long chargePayMoneyTotal;

    private Long paymentAmount;

    private Long usedPayMoney;

    private Long payMoneyBalance;

    private Long pointBalance;

    private LocalDateTime createdAt;

    public static PayMoneyDto fromEntity(PayMoney payMoney) {
        return Optional.ofNullable(payMoney)
                .map(p -> {
                    Payment payment = p.getPayment();
                    ChargeHistory chargeHistory = p.getChargeHistories();

                    return PayMoneyDto.builder()
                            .paymentId(Optional.ofNullable(payment).map(Payment::getId).orElse(null))
                            .chargeHistoryId(Optional.ofNullable(chargeHistory).map(ChargeHistory::getId).orElse(null))
                            .chargePayMoneyTotal(Optional.ofNullable(chargeHistory).map(ChargeHistory::getPayMoney).orElse(null))
                            .usedPayMoney(p.getUsedChargePayMoney())
                            .payMoneyBalance(p.getPayMoneyBalance())
                            .pointBalance(p.getPointBalance())
                            .createdAt(Optional.ofNullable(p.getCreatedAt()).map(LocalDateTime::from).orElse(null))
                            .build();
                })
                .orElse(null);
    }
}
