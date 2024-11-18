package com.varc.brewnetapp.domain.member.command.application.controller;

import com.varc.brewnetapp.common.ResponseMessage;
import com.varc.brewnetapp.domain.member.command.application.dto.ChangeMemberRequestDTO;
import com.varc.brewnetapp.domain.member.command.application.dto.ChangePwRequestDTO;
import com.varc.brewnetapp.domain.member.command.application.dto.ConfirmEmailRequestDTO;
import com.varc.brewnetapp.domain.member.command.application.dto.CreateCompanyRequestDTO;
import com.varc.brewnetapp.domain.member.command.application.dto.LoginIdRequestDTO;
import com.varc.brewnetapp.domain.member.command.application.dto.SendEmailRequestDTO;
import com.varc.brewnetapp.domain.member.command.application.service.CompanyService;
import com.varc.brewnetapp.domain.member.command.application.service.EmailService;
import com.varc.brewnetapp.domain.member.command.application.service.MemberService;
import com.varc.brewnetapp.exception.InvalidEmailCodeException;
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
@RequestMapping("api/v1")
@RestController(value = "commandMemberController")
public class MemberController {

    private final MemberService memberService;
    private final EmailService emailService;
    private final CompanyService companyService;

    @Autowired
    public MemberController(MemberService memberService, EmailService emailService, CompanyService companyService) {
        this.memberService = memberService;
        this.emailService = emailService;
        this.companyService = companyService;
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
        if (memberService.changePassword(changePwRequestDTO)) {
            return ResponseEntity.ok(new ResponseMessage<>(200, "비밀번호 변경에 성공했습니다", null));
        } else {
            throw new InvalidEmailCodeException("비밀번호 변경에 실패했습니다");
        }
    }

    @DeleteMapping("/member")
    @Operation(summary = "회원 계정 비활성화 API")
    public ResponseEntity<ResponseMessage<Object>> deleteMember(@RequestHeader("Authorization") String accessToken,
        @RequestBody LoginIdRequestDTO loginIdRequestDTO){

        memberService.deleteMember(accessToken, loginIdRequestDTO);

        return ResponseEntity.ok(new ResponseMessage<>(200, "계정 삭제 완료", null));
    }

    @PutMapping("/member")
    @Operation(summary = "회원 정보 수정 API")
    public ResponseEntity<ResponseMessage<Object>> changeMember(@RequestHeader("Authorization") String accessToken,
                                                                @RequestBody ChangeMemberRequestDTO changeMemberRequestDTO) {

        memberService.changeMember(accessToken, changeMemberRequestDTO);
        return ResponseEntity.ok(new ResponseMessage<>(200, "회원 정보 수정 성공", null));
    }

    @PostMapping("/company")
    @Operation(summary = "회사 정보 생성 API")
    public ResponseEntity<ResponseMessage<Object>> createCompany(@RequestHeader("Authorization") String accessToken,
        @RequestBody CreateCompanyRequestDTO createCompanyRequestDTO) {

        companyService.createCompany(accessToken, createCompanyRequestDTO);
        return ResponseEntity.ok(new ResponseMessage<>(200, "회사 정보 생성 성공", null));
    }

    @PutMapping("/company")
    @Operation(summary = "회사 정보 수정 API")
    public ResponseEntity<ResponseMessage<Object>> updateCompany(@RequestHeader("Authorization") String accessToken,
        @RequestBody CreateCompanyRequestDTO createCompanyRequestDTO) {

        companyService.updateCompany(accessToken, createCompanyRequestDTO);
        return ResponseEntity.ok(new ResponseMessage<>(200, "회사 정보 수정 성공", null));
    }




}
