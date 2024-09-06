package com.github.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "seller")
public class Seller {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "shop_name", length = 255)
  private String shopName;

  @Column(name = "address", length = 255)
  private String address;

  @Column(name = "address_detail", length = 255)
  private String addressDetail;

  @Column(name = "shop_image_url", length = 255)
  private String shopImageUrl;

  @OneToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;
}
