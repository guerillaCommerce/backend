package com.github.commerce.web.dto.payment;

import com.github.commerce.entity.PointHistory;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PointHistoryDto {

    private Long payMoneyId;

    private Long earnedPoint;

    private Long usedPoint;

    private LocalDateTime createdAt;

    // 적립 | 사용 선택
    private String status;

    public static PointHistoryDto fromEntity(PointHistory pointHistory) {
        return PointHistoryDto.builder()
                .payMoneyId(pointHistory.getPayMoney().getId())
                .earnedPoint(pointHistory.getEarnedPoint())
                .usedPoint(pointHistory.getUsedPoint())
                .createdAt(pointHistory.getCreatedAt())
                .status(PointStatusEnum.getByCode(pointHistory.getStatus()))
                .build();
    }


}
