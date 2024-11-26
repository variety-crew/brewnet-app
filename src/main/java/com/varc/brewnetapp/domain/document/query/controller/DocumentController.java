package com.varc.brewnetapp.domain.document.query.controller;

import com.varc.brewnetapp.common.ResponseMessage;
import com.varc.brewnetapp.domain.document.query.dto.ApproverDTO;
import com.varc.brewnetapp.domain.document.query.service.ApprovalService;
import com.varc.brewnetapp.domain.document.query.dto.ApprovalDTO;
import com.varc.brewnetapp.domain.purchase.common.KindOfApproval;
import com.varc.brewnetapp.domain.document.query.dto.ApproverMemberDTO;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController(value = "queryDocumentController")
@RequestMapping("api/v1/hq/document")
public class DocumentController {
    private final ApprovalService approvalService;

    @Autowired
    public DocumentController(ApprovalService approvalService) {
        this.approvalService = approvalService;
    }

    @GetMapping("/approval")
    @Operation(summary = "(api path 변경됨) 결재 라인 조회 API. 본사 인원만 가능")
    public ResponseEntity<ResponseMessage<List<ApprovalDTO>>> findApprovalList() {

        return ResponseEntity.ok(new ResponseMessage<>(200, "결재 목록 조회 성공", approvalService.findApprovalList()));
    }

    @GetMapping("/approver")
    @Operation(summary = "(api path 변경됨) 결재 가능자 API. 본사 인원만 가능")
    public ResponseEntity<ResponseMessage<List<ApproverDTO>>> findApprover() {

        return ResponseEntity.ok(new ResponseMessage<>(200, "결재 목록 조회 성공", approvalService.findApproverlList()));
    }

    @GetMapping("/approvers")
    @Operation(summary = "결재라인 선택 시 결재자인 회원 목록 조회 API")
    public ResponseEntity<ResponseMessage<List<ApproverMemberDTO>>> selectApproverList(
                                                        @RequestParam KindOfApproval approvalLine) {

        List<ApproverMemberDTO> approverList = approvalService.selectApproverList(approvalLine);

        return ResponseEntity.ok(new ResponseMessage<>(
                                    200, "해당 결재라인의 결재자 목록 조회 성공", approverList));
    }
}
