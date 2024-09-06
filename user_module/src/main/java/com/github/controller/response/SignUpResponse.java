package com.github.controller.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignUpResponse {
  private final String accessToken;
  private final String refreshToken;

  public static SignUpResponse from(final String accessToken, final String refreshToken) {
    return SignUpResponse.builder().accessToken(accessToken).refreshToken(refreshToken).build();
  }
}
