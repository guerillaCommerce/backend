package com.github.controller;

import com.github.common.response.Response;
import com.github.controller.response.LogInResponse;
import com.github.controller.response.SignUpResponse;
import com.github.dto.LogInDto;
import com.github.dto.SignUpDto;
import com.github.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/api/user")
public class UserController {
  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/signup")
  public Response<SignUpResponse> signup(@RequestBody final SignUpDto signUpDto) {
    final SignUpResponse response = userService.signup(signUpDto);
    return Response.success(response);
  }

  @PostMapping("/login")
  public Response<LogInResponse> login(@RequestBody final LogInDto logInDto) {
    final LogInResponse response = userService.login(logInDto);
    return Response.success(response);
  }
}
