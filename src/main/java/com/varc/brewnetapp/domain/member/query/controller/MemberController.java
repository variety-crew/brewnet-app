package com.varc.brewnetapp.domain.member.query.controller;

import com.varc.brewnetapp.common.ResponseMessage;
import com.varc.brewnetapp.domain.member.command.domain.aggregate.ApprovalStatus;
import com.varc.brewnetapp.domain.member.query.dto.ApprovalDTO;
import com.varc.brewnetapp.domain.member.query.dto.CompanyDTO;
import com.varc.brewnetapp.domain.member.query.dto.MemberDTO;
import com.varc.brewnetapp.domain.member.query.dto.OrderPrintDTO;
import com.varc.brewnetapp.domain.member.query.dto.SealDTO;
import com.varc.brewnetapp.domain.member.query.service.CompanyService;
import com.varc.brewnetapp.domain.member.query.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("queryMemberController")
@RequestMapping("api/v1")
public class MemberController {

    private final MemberService memberService;
    private final CompanyService companyService;

    @Autowired
    public MemberController(MemberService memberService, CompanyService companyService) {
        this.memberService = memberService;
        this.companyService = companyService;
    }

    @GetMapping("/member")
    @Operation(summary = "멤버 목록 조회 API / query param으로 page와 size를 키값으로 데이터 보내주시면 됩니다 "
        + "/ page는 0부터 시작 / search는 직원명. 필수는 X " 
        + "/ sort 값은 nameASC나 nameDESC로 보내주세요")
    public ResponseEntity<ResponseMessage<Page<MemberDTO>>> findMemberList(@PageableDefault(page = 0, size = 10) Pageable page,
        @RequestParam(required = false) String search,
        @RequestParam(required = false) String sort) {
        // 페이지네이션
        Page<MemberDTO> result = memberService.findMemberList(page, search, sort);
        return ResponseEntity.ok(new ResponseMessage<>(200, "멤버 목록 조회 성공", result));
    }

    @GetMapping("/company")
    @Operation(summary = "회사 정보 조회 API")
    public ResponseEntity<ResponseMessage<CompanyDTO>> findCompany() {
        return ResponseEntity.ok(new ResponseMessage<>(200, "회사 정보 조회 성공", companyService.findCompany()));
    }

    @GetMapping("/company/seal")
    @Operation(summary = "법인 인감 조회 API")
    public ResponseEntity<ResponseMessage<SealDTO>> findCompanySeal() {
        return ResponseEntity.ok(new ResponseMessage<>(200, "인감 조회 성공", companyService.findCompanySeal()));
    }

    @GetMapping("/member/detail")
    @Operation(summary = "멤버 상세 조회 API / 토큰에 들어 있는 아이디 값에 해당하는 유저의 정보를 보여줌")
    public ResponseEntity<ResponseMessage<MemberDTO>> findMember(@RequestHeader("Authorization") String accessToken) {

        return ResponseEntity.ok(new ResponseMessage<>(200, "멤버 조회 성공", memberService.findMember(accessToken)));
    }

    @GetMapping("/company/seal/history")
    @Operation(summary = "법인 인감 사용 내역 API / query param으로 page와 size를 키값으로 데이터 보내주시면 됩니다 " 
        + "/ page는 0부터 시작 / startDate와 endDate는 둘 다 보내주시거나 둘 다 안보내주시면 됩니다." 
        + " 둘 중 하나만 보내면 예외처리됩니다. Date 값 포맷은 2024-01-01로 보내주시면 됩니다"
        + "/ sort 값은 nameASC나 nameDESC로 보내주세요")
    public ResponseEntity<ResponseMessage<Page<OrderPrintDTO>>> findSealHistory(
        @PageableDefault(size = 10, page = 0) Pageable page,
        @RequestParam(required = false) String startDate,
        @RequestParam(required = false) String endDate,
        @RequestParam(required = false) String sort) {

        return ResponseEntity.ok(new ResponseMessage<>(200, "법인 인감 사용 내역 조회 성공"
            , memberService.findSealHistory(page, startDate, endDate, sort)));
    }

    @GetMapping("/member/my-draft")
    @Operation(summary = "내 기안서(내가 결재 요청한 내역) API / "
        + "/ page는 0부터 시작 / dateSort(ASC, DESC) 중 하나를 보내면 되고, 필수 X "
        + "approval은 (UNCONFIRMED, CANCELED, APPROVED, REJECTED) 값 중 하나를 보내면 되고, 필수 X "
        + "/ startDate와 endDate는 둘 다 보내주시거나 둘 다 안보내주시면 됩니다."
        + " 둘 중 하나만 보내면 예외처리됩니다. Date 값 포맷은 2024-01-01로 보내주시면 됩니다")
    public ResponseEntity<ResponseMessage<Page<ApprovalDTO>>> findMyDraft(
        @PageableDefault(size = 10, page = 0) Pageable page,
        @RequestParam(required = false) String dateSort,
        @RequestParam(required = false) String approval,
        @RequestParam(required = false) String startDate,
        @RequestParam(required = false) String endDate,
        @RequestHeader("Authorization") String accessToken) {

        return ResponseEntity.ok(new ResponseMessage<>(200, "내 기안서 조회 성공"
            , memberService.findMyDraft(page, dateSort, approval, startDate, endDate, accessToken)));
    }

    @GetMapping("/member/my-approval")
    @Operation(summary = "내 결재서(내가 결재하거나 결재해야하는 내역) API / "
        + "/ page는 0부터 시작 / dateSort(asc, desc) 중 하나를 보내면 되고, 필수 X "
        + "approval은 (UNCONFIRMED, CANCELED, APPROVED, REJECTED) 값 중 하나를 보내면 되고, 필수 X "
        + "/ startDate와 endDate는 둘 다 보내주시거나 둘 다 안보내주시면 됩니다."
        + " 둘 중 하나만 보내면 예외처리됩니다. Date 값 포맷은 2024-01-01로 보내주시면 됩니다")
    public ResponseEntity<ResponseMessage<Page<ApprovalDTO>>> findMyApproval(
        @PageableDefault(size = 10, page = 0) Pageable page,
        @RequestParam(required = false) String dateSort,
        @RequestParam(required = false) String approval,
        @RequestParam(required = false) String startDate,
        @RequestParam(required = false) String endDate,
        @RequestHeader("Authorization") String accessToken) {

        return ResponseEntity.ok(new ResponseMessage<>(200, "내 결재서 조회 성공"
            , memberService.findMyApproval(page, dateSort, approval, startDate, endDate, accessToken)));
    }



}
