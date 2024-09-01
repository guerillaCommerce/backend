package com.github.commerce.web.dto.payment;

import com.github.commerce.entity.Payment;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDto {

    private Long id;

    private List<Long> orderIdList;

    private Long payMoneyId;

    private Long couponId;

    private Long point;

    private Long payMoney;


    private Long payMoneyAmount;

    @Enumerated(EnumType.STRING)
    private String paymentMethod;

    private Long payMoneyBalance;

    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private String status;

    public static PaymentDto fromEntity(Payment payment) {
        return PaymentDto.builder()
                .id(payment.getId())
                .payMoneyId(payment.getPayMoney().getId())
                .paymentMethod(PaymentMethodEnum.getByCode(payment.getPaymentMethod()))
                .status(PaymentStatusEnum.getByCode(payment.getStatus()))
                .payMoneyAmount(payment.getPayMoneyAmount())
                .createdAt(payment.getCreatedAt())
                .payMoneyBalance(payment.getPayMoney().getPayMoneyBalance())
                .point(payment.getPayMoney().getPointBalance())
                .build();
    }

}
