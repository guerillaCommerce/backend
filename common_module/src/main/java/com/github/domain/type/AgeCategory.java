package com.github.domain.type;

public enum AgeCategory {
    TEN,
    TWENTY,
    THIRTY,
    ALL;

    private static final AgeCategory DEFAULT_CATEGORY = ALL;

    public static AgeCategory fromQueryString(final String code) {
        try {
            return AgeCategory.valueOf(code.toUpperCase());
        } catch (IllegalArgumentException e) {
            return DEFAULT_CATEGORY;
        }
    }
}
