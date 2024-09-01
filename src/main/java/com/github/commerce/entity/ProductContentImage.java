package com.github.commerce.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "product_content_images")
public class ProductContentImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @NotNull
    @Column(name = "image_url", nullable = false, length = 255)
    private String imageUrl;

    public static ProductContentImage from(Product product, String imageUrl) {
        return ProductContentImage.builder()
                .product(product)
                .imageUrl(imageUrl)
                .build();
    }
}
