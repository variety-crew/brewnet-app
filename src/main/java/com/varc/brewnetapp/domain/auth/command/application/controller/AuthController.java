package com.varc.brewnetapp.domain.auth.command.application.controller;

import com.varc.brewnetapp.common.ResponseMessage;
import com.varc.brewnetapp.domain.auth.command.application.dto.ChangePwRequestDTO;
import com.varc.brewnetapp.domain.auth.command.application.dto.ConfirmEmailRequestDTO;
import com.varc.brewnetapp.domain.auth.command.application.dto.GrantAuthRequestDTO;
import com.varc.brewnetapp.domain.auth.command.application.dto.SendEmailRequestDTO;
import com.varc.brewnetapp.domain.auth.command.application.dto.SignUpRequestDto;
import com.varc.brewnetapp.domain.auth.command.application.service.AuthService;
import com.varc.brewnetapp.domain.auth.command.application.service.EmailService;
import com.varc.brewnetapp.exception.InvalidEmailCodeException;
import com.varc.brewnetapp.security.utility.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    @Operation(summary = "회원가입 API")
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

    // 아무나
    @PostMapping("/send-email")
    @Operation(summary = "인증 이메일 발송 API")
    public ResponseEntity<ResponseMessage<Object>> sendEmail(@RequestBody SendEmailRequestDTO sendEmailRequestDTO)
                                                             throws MessagingException, UnsupportedEncodingException {
        try {
            emailService.sendSimpleMessage(sendEmailRequestDTO);
            return ResponseEntity.ok(new ResponseMessage<>(200, "인증 이메일 발송에 성공했습니다", null));
        } catch (MailException e) {
            throw new MailSendException("인증 이메일 발송에 실패했습니다.");
        }
    }

    // 아무나
    @PostMapping("/confirm-email")
    @Operation(summary = "인증 이메일 검증 API")
    public ResponseEntity<ResponseMessage<Object>> confirmEmail(@RequestBody ConfirmEmailRequestDTO confirmEmailRequestDTO) {
        if (emailService.findEmailCode(confirmEmailRequestDTO))
            return ResponseEntity.ok(new ResponseMessage<>(200, "이메일 인증에 성공했습니다", null));
        else
            throw new InvalidEmailCodeException("이메일 인증에 실패했습니다");
    }

    // 아무나
    @PutMapping("/pw")
    @Operation(summary = "비밀번호 변경 API")
    public ResponseEntity<ResponseMessage<Object>> changePassword(@RequestBody ChangePwRequestDTO changePwRequestDTO) {
        if (authService.changePassword(changePwRequestDTO)) {
            return ResponseEntity.ok(new ResponseMessage<>(200, "비밀번호 변경에 성공했습니다", null));
        } else {
            throw new InvalidEmailCodeException("비밀번호 변경에 실패했습니다");
        }
    }

    //마스터만
    @PostMapping("member")
    @Operation(summary = "회원 별 권한 부여 API")
    public ResponseEntity<ResponseMessage<Object>> grantAuth(@RequestHeader("Authorization") String accessToken,
                                                             @RequestBody GrantAuthRequestDTO grantAuthRequestDTO){
        authService.grantAuth(accessToken, grantAuthRequestDTO);

        return ResponseEntity.ok(new ResponseMessage<>(200, "권한 부여 성공", null));
    }

    @DeleteMapping("member")
    @Operation(summary = "회원 삭제 API")
    public ResponseEntity<ResponseMessage<Object>> deleteMember(@RequestHeader("Authorization") String accessToken,
                                                                @RequestBody String deleteMemberId){


        authService.deleteMember(accessToken, deleteMemberId);

        return ResponseEntity.ok(new ResponseMessage<>(200, "권한 부여 성공", null));
    }
    

    

}
