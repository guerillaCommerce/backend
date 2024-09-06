package com.github.domain;

import com.github.domain.type.UserRole;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "email", nullable = false, length = 100)
  private String email;

  @Column(name = "password", nullable = false, length = 255)
  private String password;

  @Column(name = "user_name", nullable = false, length = 50)
  private String userName;

  @Enumerated(value = EnumType.STRING)
  @Column(name = "role", length = 10)
  private UserRole role;

  @Setter
  @Column(name = "refresh_token", length = 500)
  private String refreshToken;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  //    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, orphanRemoval = true)
  //    private List<UsersCoupon> userCoupons;

  //    @OneToOne(mappedBy = "users", cascade = CascadeType.ALL, orphanRemoval = true)
  //    private UsersInfo usersInfo;

  //    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
  //    private List<PayMoney> payMoney;

  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "seller_id", referencedColumnName = "id")
  private Seller seller;

  //    public PayMoney getPayMoneyByUserId() {
  //        return
  // payMoney.stream().max(Comparator.comparing(PayMoney::getId)).orElseGet(PayMoney::new);
  //    }
}
