package com.github.controller.response;

import lombok.Getter;

@Getter
public class ProductResponse {
    private final String message;

    public ProductResponse(final String message) {
        this.message = message;
    }
}