package com.github.domain.type;

public enum ProductCategory {
    SWEAT,
    HOOD,
    KNIT,
    SLEEVELESS,
    JEANS,
    SHORTS,
    TRAINING,
    LEGGINGS,
    SHORTDRESS,
    LONGDRESS,
    SHOES,
    MUFFLER,
    GLOVES,
    CAP,
    ALL;

    private static final ProductCategory DEFAULT_CATEGORY = ALL;

    public static ProductCategory fromQueryString(final String code) {
        try {
            return ProductCategory.valueOf(code.toUpperCase());
        } catch (IllegalArgumentException e) {
            return DEFAULT_CATEGORY;
        }
    }
}
