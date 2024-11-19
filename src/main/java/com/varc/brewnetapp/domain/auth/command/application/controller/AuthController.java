package com.varc.brewnetapp.domain.auth.command.application.controller;

import com.varc.brewnetapp.common.ResponseMessage;
import com.varc.brewnetapp.domain.auth.command.application.dto.GrantAuthRequestDTO;
import com.varc.brewnetapp.domain.auth.command.application.dto.SignUpRequestDto;
import com.varc.brewnetapp.domain.auth.command.application.service.AuthService;
import com.varc.brewnetapp.domain.member.command.application.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    private final EmailService emailService;

    @Autowired
    public AuthController(AuthService authService, EmailService emailService) {
        this.authService = authService;
        this.emailService = emailService;
    }

    // 회원가입 마스터 권한
    @PostMapping("sign-up")
    @Operation(summary = "회원가입 API / 가맹점 지점을 같이 보내면 가맹점으로 회원가입 됨")
    public ResponseEntity<ResponseMessage<Object>> signup(@RequestBody SignUpRequestDto signupRequestDto) {
        authService.signUp(signupRequestDto);

        return ResponseEntity.ok(new ResponseMessage<>(200, "회원 가입 성공", null));
    }

    // 토큰 필요, 권한자만
    @PostMapping("/logout")
    @Operation(summary = "로그아웃 API")
    public ResponseEntity<ResponseMessage<Object>> logout(@RequestHeader("Authorization") String accessToken) {
        authService.logout(accessToken);
        return ResponseEntity.ok(new ResponseMessage<>(200, "로그아웃 성공", null));
    }



    //마스터만
    @PostMapping("/member")
    @Operation(summary = "회원 별 권한 부여 API")
    public ResponseEntity<ResponseMessage<Object>> grantAuth(@RequestHeader("Authorization") String accessToken,
                                                             @RequestBody GrantAuthRequestDTO grantAuthRequestDTO){
        authService.grantAuth(accessToken, grantAuthRequestDTO);

        return ResponseEntity.ok(new ResponseMessage<>(200, "권한 부여 성공", null));
    }


    

}
