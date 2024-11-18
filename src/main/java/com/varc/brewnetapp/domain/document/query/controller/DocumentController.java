package com.varc.brewnetapp.domain.document.query.controller;

import com.varc.brewnetapp.common.ResponseMessage;
import com.varc.brewnetapp.domain.document.query.service.ApprovalService;
import com.varc.brewnetapp.domain.document.query.dto.ApprovalDTO;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "queryDocumentController")
@RequestMapping("api/v1/document")
public class DocumentController {
    private final ApprovalService approvalService;

    @Autowired
    public DocumentController(ApprovalService approvalService) {
        this.approvalService = approvalService;
    }

    @GetMapping("/approval")
    @Operation(summary = "결재 라인 조회 API")
    public ResponseEntity<ResponseMessage<List<ApprovalDTO>>> findApprovalList() {

        return ResponseEntity.ok(new ResponseMessage<>(200, "결재 목록 조회 성공", approvalService.findApprovalList()));
    }
}
