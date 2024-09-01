package com.github.commerce.web.controller.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.commerce.config.security.JwtUtil;
import com.github.commerce.entity.User;
import com.github.commerce.repository.user.UserDetailsImpl;
import com.github.commerce.service.user.OAuthService;
import com.github.commerce.service.user.UserService;
import com.github.commerce.service.user.exception.UserErrorCode;
import com.github.commerce.service.user.exception.UserException;
import com.github.commerce.web.dto.user.*;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/v1/api/user")
@RestController
public class UserController {

    private final UserService userService;
    private final OAuthService oAuthService;

    @PostMapping(value = "/register-seller")
    public ResponseEntity<String> registerSeller(@RequestPart RegisterSellerDto registerSellerDto, @RequestPart(required = false) MultipartFile multipartFile) {
        return ResponseEntity.ok(userService.registerSeller(registerSellerDto, multipartFile));
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterUserInfoDto registerUserInfoDto) {
        return ResponseEntity.ok(userService.registerUser(registerUserInfoDto));
    }

    @PostMapping(value = "/login")
    public ResponseEntity<TokenDto> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse httpServletResponse) {
        TokenDto tokenDto = userService.login(loginRequestDto);
        httpServletResponse.setHeader(JwtUtil.ACCESS_TOKEN, tokenDto.getAccessToken());
        httpServletResponse.setHeader(JwtUtil.REFRESH_TOKEN, tokenDto.getRefreshToken());

        return ResponseEntity.ok(tokenDto);
    }

    @GetMapping(value = "/getInfo")
    @PreAuthorize("isAuthenticated()")//메소드 실행 전 로그인되어야함
    public ResponseEntity<MyInfoResponseDto> getMyInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        if (user.getIsDelete() == true)
            throw new UserException(UserErrorCode.UER_NOT_FOUND);
        return ResponseEntity.ok(userService.getMyInfo(user.getId(), user.getRole().name()));

    }

    @GetMapping(value = "/checkEmail")
    public ResponseEntity<String> checkEmail(@RequestParam String email) {
        return ResponseEntity.ok(userService.checkEmail(email));
    }

    @GetMapping(value = "/checkNickname")
    public ResponseEntity<String> checkNickName(@RequestParam String nickname) {
        return ResponseEntity.ok(userService.checkNickName(nickname));
    }

    @GetMapping(value = "/checkShopName")
    public ResponseEntity<String> checkShopName(@RequestParam String shopName) {
        return ResponseEntity.ok(userService.checkShopName(shopName));
    }


    @PatchMapping(value = "/updatePassword")
    public ResponseEntity<String> updatePassword(@RequestBody UpdatePasswordReq updatePasswordReq, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(userService.updatePassword(updatePasswordReq, userDetails));
    }


    @GetMapping(value = "/getSellerInfo")
    public ResponseEntity<SellerInfo> getSellerInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(userService.getSellerInfo(userDetails.getUser().getId()));
    }


    @PatchMapping(value = "/updateSeller")
    public ResponseEntity<String> updateSeller(@RequestPart SellerInfo sellerInfo, @RequestPart(required = false) MultipartFile shopImgFile, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(userService.updateSeller(sellerInfo, shopImgFile, userDetails.getUser().getId()));
    }


    @GetMapping(value = "/getUserInfo")
    public ResponseEntity<UserInfo> getUserInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(userService.getUserInfo(userDetails.getUser().getId()));
    }


    @PatchMapping(value = "/update")
    public ResponseEntity<String> updateUserInfo(@RequestBody UserInfo userInfo, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(userService.updateUserInfo(userInfo, userDetails.getUser().getId()));
    }

    @GetMapping("/kakao/callback")
    public ResponseEntity<TokenDto> kakaoLogin(@RequestParam String code, HttpServletResponse httpServletResponse) throws JsonProcessingException {
        TokenDto tokenDto = oAuthService.kakaoLogin(code);

        httpServletResponse.setHeader(JwtUtil.ACCESS_TOKEN, tokenDto.getAccessToken());
        httpServletResponse.setHeader(JwtUtil.REFRESH_TOKEN, tokenDto.getRefreshToken());

        return ResponseEntity.ok(tokenDto);
    }

}