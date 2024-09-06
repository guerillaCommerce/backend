package com.github.service.usecase;

import com.github.domain.User;
import com.github.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class UpdateRefreshTokenUseCase {
  private final UserRepository userRepository;

  public UpdateRefreshTokenUseCase(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  private void updateRefreshToken(final String refreshToken, final User user) {
    user.setRefreshToken(refreshToken);
    userRepository.save(user);
  }

  public void exec(final String refreshToken, final User user) {
    this.updateRefreshToken(refreshToken, user);
  }
}
