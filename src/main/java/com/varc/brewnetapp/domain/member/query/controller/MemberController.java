package com.varc.brewnetapp.domain.member.query.controller;

import com.varc.brewnetapp.common.ResponseMessage;
import com.varc.brewnetapp.domain.auth.command.application.dto.ConfirmEmailRequestDTO;
import com.varc.brewnetapp.domain.member.query.service.MemberService;
import com.varc.brewnetapp.exception.InvalidEmailCodeException;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("queryMemberController")
@RequestMapping("api/v1/member")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

//    @GetMapping("")
//    @Operation(summary = "멤버 조회 API")
//    public ResponseEntity<ResponseMessage<Object>> confirmEmail(@RequestBody ConfirmEmailRequestDTO confirmEmailRequestDTO) {
//        if (emailService.findEmailCode(confirmEmailRequestDTO))
//            return ResponseEntity.ok(new ResponseMessage<>(200, "이메일 인증에 성공했습니다", null));
//        else
//            throw new InvalidEmailCodeException("이메일 인증에 실패했습니다");
//    }
}
