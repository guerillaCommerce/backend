package com.github.service.usecase;

import com.github.common.exception.GlobalException;
import com.github.domain.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class ValidatePasswordUseCase {
  private final BCryptPasswordEncoder passwordEncoder;

  public ValidatePasswordUseCase(BCryptPasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  private void validatePassword(final String password, final User user) {
    boolean isValid = passwordEncoder.matches(password, user.getPassword());
    if (!isValid) throw new GlobalException(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");
  }

  public void exec(final String password, final User user) {
    this.validatePassword(password, user);
  }
}
