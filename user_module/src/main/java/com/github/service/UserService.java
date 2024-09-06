package com.github.service;

import com.github.controller.response.LogInResponse;
import com.github.controller.response.SignUpResponse;
import com.github.domain.User;
import com.github.dto.LogInDto;
import com.github.dto.SignUpDto;
import com.github.service.usecase.*;
import com.github.util.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
  private final GetUserByEmailUseCase getUserByEmailUseCase;
  private final CreateUserUseCase createUserUseCase;
  private final ValidateSignUpUseCase validateSignUpUseCase;
  private final ValidateDuplicatedEmailUseCase validateDuplicatedEmailUseCase;
  private final JwtTokenProvider jwtTokenProvider;
  private final PasswordEncoder passwordEncoder;
  private final ValidatePasswordUseCase validatePasswordUseCase;
  private final UpdateRefreshTokenUseCase updateRefreshTokenUseCase;

  public LogInResponse login(final LogInDto logInDto) {

    // 1.사용자가 존재하는지 검증
    final User user = getUserByEmailUseCase.exec(logInDto.getEmail());

    // 2.1에서 조회된 사용자의 비밀번호와 새로 입력된 비밀번호 대조
    validatePasswordUseCase.exec(logInDto.getPassword(), user);

    final String accessToken = jwtTokenProvider.createAccessToken(user.getEmail());
    final String refreshToken = jwtTokenProvider.createRefreshToken(user.getEmail());
    updateRefreshTokenUseCase.exec(refreshToken, user);

    return LogInResponse.from(accessToken, refreshToken);
  }

  public SignUpResponse signup(final SignUpDto signUpDto) {
    // 이메일과 비밀번호 형식 검증
    this.validateSignUpUseCase.exec(signUpDto);

    // DB조회 이메일 중복검사
    this.validateDuplicatedEmailUseCase.exec(signUpDto.getEmail());

    // 비밀번호 암호화
    final String encodedPassword = passwordEncoder.encode(signUpDto.getPassword());

    // 저장
    final User user = SignUpDto.toEntity(signUpDto, encodedPassword);
    final String accessToken = this.jwtTokenProvider.createAccessToken(user.getEmail());
    final String refreshToken = this.jwtTokenProvider.createRefreshToken(user.getEmail());
    user.setRefreshToken(refreshToken);
    this.createUserUseCase.exec(user);

    return SignUpResponse.from(accessToken, refreshToken);
  }
}
