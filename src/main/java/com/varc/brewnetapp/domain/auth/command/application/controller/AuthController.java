package com.varc.brewnetapp.domain.auth.command.application.controller;

import com.varc.brewnetapp.common.ResponseMessage;
import com.varc.brewnetapp.domain.auth.command.application.dto.GrantAuthRequestDTO;
import com.varc.brewnetapp.domain.auth.command.application.dto.SignUpRequestDto;
import com.varc.brewnetapp.domain.auth.command.application.service.AuthService;
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
@RequestMapping("api/v1")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;

    }

    // 회원가입 마스터 권한
    @PostMapping("/auth/sign-up")
    @Operation(summary = "회원가입 API / franchise name을 같이 보내면 가맹점으로 회원가입 됨 " 
        + "/ 본사 직원은 position name을 보내주시면 됩니다 / 단, franchise name과 position name을 둘 다 보내면 Error " 
        + "/ position name은 사원, 대리, 과장, 대표 중 선택해서 보내주시면 됩니다")
    public ResponseEntity<ResponseMessage<Object>> signup(@RequestBody SignUpRequestDto signupRequestDto) {
        authService.signUp(signupRequestDto);

        return ResponseEntity.ok(new ResponseMessage<>(200, "회원 가입 성공", null));
    }

    // 토큰 필요, 권한자만
    @PostMapping("/auth/logout")
    @Operation(summary = "로그아웃 API")
    public ResponseEntity<ResponseMessage<Object>> logout(@RequestHeader("Authorization") String accessToken) {
        authService.logout(accessToken);
        return ResponseEntity.ok(new ResponseMessage<>(200, "로그아웃 성공", null));
    }



    //마스터만
    @PostMapping("/master/auth/member")
    @Operation(summary = "(api path 변경됨) 회원 별 권한 부여 API. master만 사용 가능 "
        + "/ 권한 값은 master, generalAdmin, responsibleAdmin, delivery만 보내주세요 "
        + "/ 가맹점은 가맹점 계정 생성 시에만 권한이 부여됨")
    public ResponseEntity<ResponseMessage<Object>> grantAuth(@RequestHeader("Authorization") String accessToken,
                                                             @RequestBody GrantAuthRequestDTO grantAuthRequestDTO){
        authService.grantAuth(accessToken, grantAuthRequestDTO);

        return ResponseEntity.ok(new ResponseMessage<>(200, "권한 부여 성공", null));
    }


    

}
