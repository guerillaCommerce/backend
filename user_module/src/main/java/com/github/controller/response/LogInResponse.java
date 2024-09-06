package com.github.controller.response;

import lombok.*;

@Getter
@Builder
public class LogInResponse {

  private final String accessToken;
  private final String refreshToken;

  public static LogInResponse from(final String accessToken, final String refreshToken) {
    return new LogInResponse(accessToken, refreshToken);
  }

  public static LogInResponse from(final String accessToken) {
    return LogInResponse.builder().accessToken(accessToken).build();
  }
}
