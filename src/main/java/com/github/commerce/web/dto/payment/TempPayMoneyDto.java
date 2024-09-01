package com.github.commerce.web.dto.payment;

import com.github.commerce.entity.PayMoney;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TempPayMoneyDto {

    private Long payMoneyBalance;
    
    private LocalDateTime createAt;


    public static TempPayMoneyDto fromEntity(PayMoney payMoney) {

        return TempPayMoneyDto.builder()
                .payMoneyBalance(payMoney.getPayMoneyBalance())
                .createAt(LocalDateTime.from(payMoney.getCreatedAt()))
                .build();

    }

}
