package com.github.dto;

import com.github.domain.User;
import com.github.domain.type.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpDto {
  private String email;
  private String password;
  private String userName;

  public static User toEntity(SignUpDto signUpDto, String encodedPassword) {
    return User.builder()
        .email(signUpDto.getEmail())
        .password(encodedPassword)
        .userName(signUpDto.getUserName())
        .role(UserRole.USER)
        .createdAt(LocalDateTime.now())
        .build();
  }
}
