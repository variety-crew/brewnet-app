package com.varc.brewnetapp.domain.member.command.application.controller;

import com.varc.brewnetapp.domain.auth.command.application.dto.SignUpRequestDto;
import com.varc.brewnetapp.domain.member.command.application.service.MemberService;
import com.varc.brewnetapp.security.utility.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("api/v1/member")
@RestController(value = "commandMemberController")
public class MemberController {

    private final MemberService memberService;
    private final JwtUtil jwtUtil;

    @Autowired
    public MemberController(MemberService memberService, JwtUtil jwtUtil) {
        this.memberService = memberService;
        this.jwtUtil = jwtUtil;
    }

    // 회원가입
    @DeleteMapping("/logout")
    public void logout(@RequestHeader("Authorization") String accessToken) {
        log.debug("컨트롤러당도함!");
        String token = accessToken.replace("Bearer ", "");
        String loginId = jwtUtil.getLoginId(token);
        memberService.logout(loginId);
    }

    @GetMapping("/health")
    public void health() {
        log.debug("컨트롤러당도함!");
    }
}
