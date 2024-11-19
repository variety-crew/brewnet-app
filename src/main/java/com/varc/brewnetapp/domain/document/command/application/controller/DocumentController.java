package com.varc.brewnetapp.domain.document.command.application.controller;

import com.varc.brewnetapp.common.ResponseMessage;
import com.varc.brewnetapp.domain.document.command.application.dto.ApproverRequestDTO;
import com.varc.brewnetapp.domain.document.command.application.service.ApprovalService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "commandDocumentController")
@RequestMapping("api/v1/document")
public class DocumentController {
    private final ApprovalService approvalService;

    @Autowired
    public DocumentController(ApprovalService approvalService) {
        this.approvalService = approvalService;
    }

    @PostMapping("/approver")
    @Operation(summary = "결재 별 결재 직급 설정 API / [positionName]은 대리, 과장, 대표이사만 가능 / "
        + "[seq]는 1 ~ 4만 가능 / [kind]는 주문, 반품, 교환, 발주 만 가능  "
        + "또한, kind와 seq가 같은 값은 한 개 밖에 존재못함. 즉, kind와 seq가 같은 값이 DB에 있다면 자동으로 수정됨. \n" 
        + "[해당 API가 생성 및 수정을 담당]")
    public ResponseEntity<ResponseMessage<Object>> createApprover(@RequestHeader("Authorization") String accessToken,
        @RequestBody ApproverRequestDTO approverRequestDTO) {

        approvalService.createApprover(accessToken, approverRequestDTO);
        return ResponseEntity.ok(new ResponseMessage<>(200, "결재자 생성 or 수정 성공", null));
    }

    @DeleteMapping("/approver")
    @Operation(summary = "결재 별 결재 직급 설정 API / "
        + "[seq]는 1 ~ 4만 가능 / [kind]는 주문, 반품, 교환, 발주 만 가능 " 
        + "해당 API는 Hard Delete 됨")
    public ResponseEntity<ResponseMessage<Object>> deleteApprover(@RequestHeader("Authorization") String accessToken,
        @RequestBody ApproverRequestDTO approverRequestDTO) {

        approvalService.deleteApprover(accessToken, approverRequestDTO);
        return ResponseEntity.ok(new ResponseMessage<>(200, "결재자 삭제 성공", null));
    }
}
