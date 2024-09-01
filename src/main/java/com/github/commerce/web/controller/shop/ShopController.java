package com.github.commerce.web.controller.shop;

import com.github.commerce.repository.user.UserDetailsImpl;
import com.github.commerce.service.shop.ShopService;
import com.github.commerce.web.dto.product.SellingProductDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("v1/api/product/seller")
public class ShopController {

    private final ShopService shopService;

    @GetMapping
    public ResponseEntity<List<SellingProductDto>> getSellingProducts(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long userId = userDetails.getUser().getId();
        return ResponseEntity.ok(shopService.getSellingProducts(userId));
    }

}
