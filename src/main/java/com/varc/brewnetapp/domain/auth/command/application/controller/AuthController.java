package com.varc.brewnetapp.domain.auth.command.application.controller;

import com.varc.brewnetapp.domain.auth.command.application.dto.SignUpRequestDto;
import com.varc.brewnetapp.domain.auth.command.application.service.AuthService;
import com.varc.brewnetapp.security.utility.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
    private final AuthService authService;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthController(AuthService authService, JwtUtil jwtUtil) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
    }

    // 회원가입
    @PostMapping("sign-up")
    public void signup(@RequestBody SignUpRequestDto signupRequestDto) {
        authService.signUp(signupRequestDto);
    }

    @DeleteMapping("/logout")
    public void logout(@RequestHeader("Authorization") String accessToken) {
        String token = accessToken.replace("Bearer ", "");
        String loginId = jwtUtil.getLoginId(token);
        authService.logout(loginId);
    }
}
