package com.github.service.usecase;

import com.github.common.exception.GlobalException;
import com.github.domain.User;
import com.github.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class GetUserByEmailUseCase {
  private final UserRepository userRepository;

  public GetUserByEmailUseCase(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  private User getUserByEmail(String email) {
    return userRepository
        .findOneByEmail(email)
        .orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND, "User not found"));
  }

  public User exec(String email) {
    return this.getUserByEmail(email);
  }
}
