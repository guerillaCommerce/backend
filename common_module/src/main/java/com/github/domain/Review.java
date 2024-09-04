package com.github.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "users_id")
//    private User users;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "orders_id")
//    private Order orders;

    @Column(name = "author", length = 100)
    private String author;

    @Column(name = "content", length = 1000)
    private String content;

    @Column(name = "star_point", nullable = false, columnDefinition = "int default 0")
    private Short starPoint;

    @Builder.Default
    @Column(name = "is_deleted", nullable = false, columnDefinition = "tinyint default 0")
    private Boolean isDeleted = false;

    @Setter
    @Column(name = "image_url", length = 200)
    private String imageUrl;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}