package com.github.service.usecase;

import com.github.common.exception.GlobalException;
import com.github.dto.SignUpDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ValidateSignUpUseCase {
  private static final int MIN_PASSWORD_LENGTH = 8;
  private static final int MAX_PASSWORD_LENGTH = 20;
  // (.+) : 하나 이상의 어떤 문자든지 포함하는 하나의 그룹 //1.아무 문자(숫자포함)나 1개 이상 @ 앞뒤로 있을 것 2.반드시 @가 중간에 있을 것
  private static final String EMAIL_PATTERN = "^(.+)@(.+)$";

  private void validatePassword(String password) {
    if (password.length() < MIN_PASSWORD_LENGTH || password.length() > MAX_PASSWORD_LENGTH) {
      throw new GlobalException(HttpStatus.BAD_REQUEST, "비밀번호는 8글자 이상, 20글자 이하");
    }
  }

  private void validateEmail(String email) {
    if (!email.matches(EMAIL_PATTERN)) {
      throw new GlobalException(HttpStatus.BAD_REQUEST, "올바른 이메일 형식이 아닙니다.");
    }
  }

  public void exec(SignUpDto signUpDto) {
    this.validateEmail(signUpDto.getEmail());
    this.validatePassword(signUpDto.getPassword());
  }
}
