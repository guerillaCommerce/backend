package com.github.domain.type;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum AgeCategory {
    TEN,
    TWENTY,
    THIRTY,
    ALL;

    private static final AgeCategory DEFAULT_CATEGORY = ALL;

    public static AgeCategory fromQueryString(final String code) {
        try {
            log.info("Converting string '{}' to AgeCategory enum", code);
            return AgeCategory.valueOf(code.toUpperCase());
        } catch (IllegalArgumentException e) {
            return DEFAULT_CATEGORY;
        }
    }
}
