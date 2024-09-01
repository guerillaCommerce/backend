package com.github.commerce.web.dto.product;

import com.github.commerce.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SellingProductDto {

    private Long productId;

    private String shopName;

    private String name;

    private String content;

    private Integer price;

    private Integer leftAmount;

    private String thumbnailUrl;

    public SellingProductDto(Product product) {
        this.productId = product.getId();
        this.shopName = product.getSeller().getShopName();
        this.name = product.getName();
        this.content = product.getContent();
        this.price = product.getPrice();
        this.leftAmount = product.getLeftAmount();
        this.thumbnailUrl = product.getThumbnailUrl();
    }

}
