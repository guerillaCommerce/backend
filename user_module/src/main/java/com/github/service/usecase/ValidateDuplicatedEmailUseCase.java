package com.github.service.usecase;

import com.github.common.exception.GlobalException;
import com.github.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ValidateDuplicatedEmailUseCase {
  private final UserRepository userRepository;

  public ValidateDuplicatedEmailUseCase(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  private void validateDuplicatedEmail(String email) {
    userRepository
        .findOneByEmail(email)
        .ifPresent(
            (present) -> {
              throw new GlobalException(HttpStatus.CONFLICT, "이미 가입된 이메일");
            });
  }

  public void exec(String email) {
    this.validateDuplicatedEmail(email);
  }
}
