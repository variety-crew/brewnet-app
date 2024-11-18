package com.varc.brewnetapp.domain.member.query.controller;

import com.varc.brewnetapp.common.ResponseMessage;
import com.varc.brewnetapp.domain.auth.command.application.dto.ConfirmEmailRequestDTO;
import com.varc.brewnetapp.domain.member.query.dto.MemberDTO;
import com.varc.brewnetapp.domain.member.query.service.MemberService;
import com.varc.brewnetapp.exception.InvalidEmailCodeException;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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

    @GetMapping("")
    @Operation(summary = "멤버 목록 조회 API")
    public ResponseEntity<ResponseMessage<Page<MemberDTO>>> confirmEmail(@RequestHeader("Authorization") String accessToken,
                                                                   @PageableDefault(value = 10) Pageable page) {
        // 페이지네이션
        Page<MemberDTO> result = memberService.findMemberList(page);
        return ResponseEntity.ok(new ResponseMessage<>(200, "멤버 목록 조회 성공", result));
    }
}
