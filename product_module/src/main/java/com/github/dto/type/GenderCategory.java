package com.github.dto.type;

public enum GenderCategory {
    FEMALE,
    MALE,
    ALL;

    private static final GenderCategory DEFAULT_CATEGORY = ALL;

    public static GenderCategory fromQueryString(final String code) {
        try {
            return GenderCategory.valueOf(code.toUpperCase());
        } catch (IllegalArgumentException e) {
            return DEFAULT_CATEGORY;
        }
    }
}
