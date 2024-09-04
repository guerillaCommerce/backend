package com.github.domain.type;

public enum SortType {
    LATEST,
    PRICE,
    ID;

    private static final SortType DEFAULT_CATEGORY = ID;

    public static SortType fromQueryString(final String code) {
        try {
            return SortType.valueOf(code.toUpperCase());
        } catch (IllegalArgumentException e) {
            return DEFAULT_CATEGORY;
        }
    }
}
