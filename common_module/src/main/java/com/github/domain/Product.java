package com.github.domain;

import com.github.domain.type.AgeCategory;
import com.github.domain.type.GenderCategory;
import com.github.domain.type.ProductCategory;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sellers_id", nullable = false)
    private Seller seller;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "thumbnail_url", length = 255)
    private String thumbnailUrl;

    @Column(name = "image_urls", columnDefinition = "TEXT")
    private String imageUrls;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "left_amount")
    private Integer leftAmount;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "is_deleted", nullable = false, columnDefinition = "tinyint default 0")
    private Boolean isDeleted;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_category", length = 20)
    private ProductCategory productCategory;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender_category", length = 20)
    private GenderCategory genderCategory;

    @Enumerated(EnumType.STRING)
    @Column(name = "age_category", length = 20)
    private AgeCategory ageCategory;

    @Column(name = "options", columnDefinition = "TEXT")
    private String options;

    @DecimalMin("0.0")
    @DecimalMax("5.0")
    @Column(name = "average_star_point", precision = 2)
    private Double averageStarPoint;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;
}
