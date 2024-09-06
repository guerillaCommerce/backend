package com.github.service.usecase;

import com.github.common.exception.GlobalException;
import com.github.domain.User;
import com.github.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CreateUserUseCase {
  private final UserRepository userRepository;

  public CreateUserUseCase(final UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  private User createUser(User user) {
    try {
      return userRepository.save(user);
    } catch (Exception e) {
      log.error(e.getMessage());
      throw new GlobalException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to save user");
    }
  }

  public User exec(final User user) {
    return this.createUser(user);
  }
}
