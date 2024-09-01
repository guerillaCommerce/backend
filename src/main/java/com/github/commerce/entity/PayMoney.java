package com.github.commerce.entity;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "pay_moneys")
@DynamicInsert
public class PayMoney {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "users_id", nullable = false)
    private User users;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chargeHistoryId")
    private ChargeHistory chargeHistories;

    @OneToOne(mappedBy = "payMoney")
    private Payment payment;

    @Column(name = "charge_pay_money_total")
    private Long chargePayMoneyTotal;

    @Column(name = "used_charge_pay_money")
    private Long usedChargePayMoney;

    @Column(name = "pay_money_balance")
    private Long payMoneyBalance;

    @Column(name = "point_balance")
    private Long pointBalance;

    @CreatedDate
    @Column(name = "create_at")
    private LocalDateTime createdAt;


    // 새롭게 생성 될꺼라 , 유지되는 값이 들어가야 함.
    public static PayMoney usePayMoney(PayMoney payMoney){
        return PayMoney.builder()
                .users(payMoney.getUsers())
                .chargeHistories(payMoney.getChargeHistories())
                .chargePayMoneyTotal(payMoney.getChargePayMoneyTotal())
                .build();
    }

    public Long calculatePayMoneyBalance() {
        return (chargePayMoneyTotal != null ? chargePayMoneyTotal : 0L) - (usedChargePayMoney != null ? usedChargePayMoney : 0L);
    }
}
