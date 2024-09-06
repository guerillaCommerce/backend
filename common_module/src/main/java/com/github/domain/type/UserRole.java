package com.github.domain.type;

public enum UserRole {
  USER,
  SELLER;

  private static final UserRole DEFAULT_ROLE = USER;

  public static UserRole fromQueryString(final String code) {
    try {
      return UserRole.valueOf(code.toUpperCase());
    } catch (IllegalArgumentException e) {
      return DEFAULT_ROLE;
    }
  }
}
