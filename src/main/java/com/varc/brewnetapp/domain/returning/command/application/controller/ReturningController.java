package com.varc.brewnetapp.domain.returning.command.application.controller;

import com.varc.brewnetapp.common.ResponseMessage;
import com.varc.brewnetapp.domain.returning.command.application.service.ReturningService;
import com.varc.brewnetapp.domain.returning.command.domain.aggregate.vo.ReturningDrafterApproveReqVO;
import com.varc.brewnetapp.domain.returning.command.domain.aggregate.vo.ReturningManagerApproveReqVO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("ReturningControllerCommand")
@RequiredArgsConstructor
@RequestMapping("/api/v1/hq/return")
@Slf4j
public class ReturningController {

    private final ReturningService returningService;

    @PostMapping("/{returningCode}/drafter-approve")
    @Operation(summary = "[본사] 반품 결재신청(기안자) API")
    public ResponseEntity<ResponseMessage<Integer>> drafterApproveReturning(@RequestAttribute("loginId") String loginId,
                                                                           @PathVariable("returningCode") int returningCode,
                                                                           @RequestBody ReturningDrafterApproveReqVO returningApproveReqVO) {
        returningService.drafterReturning(loginId, returningCode, returningApproveReqVO);
        return ResponseEntity.ok(new ResponseMessage<>(200, "반품 결재신청 성공", null));
    }

    @PostMapping("/{returningCode}/manager-approve")
    @Operation(summary = "[본사] 반품 결재승인(결재자) API")
    public ResponseEntity<ResponseMessage<Integer>> approveReturning(@RequestAttribute("loginId") String loginId,
                                                                    @PathVariable("returningCode") int returningCode,
                                                                    @RequestBody ReturningManagerApproveReqVO returningApproveReqVO) {
        returningService.managerReturning(loginId, returningCode, returningApproveReqVO);
        return ResponseEntity.ok(new ResponseMessage<>(200, "반품 결재승인 성공", null));
    }

    @PostMapping("/other/returning-complete/{returningStockHistoryCode}")
    @Operation(summary = "[본사] 타부서 반품처리내역 상세조회 - 1. 반품완료 API")
    public ResponseEntity<ResponseMessage<Integer>> completeReturning(@RequestAttribute("loginId") String loginId,
                                                                     @PathVariable("returningStockHistoryCode") int returningStockHistoryCode) {
        returningService.completeStock(loginId, returningStockHistoryCode);
        return ResponseEntity.ok(new ResponseMessage<>(200, "반품완료 성공", null));
    }

    @PostMapping("/other/refund-complete/{returningRefundHistoryCode}")
    @Operation(summary = "[본사] 타부서 반품처리내역 상세조회 - 2. 환불완료 API")
    public ResponseEntity<ResponseMessage<Integer>> completeRefund(@RequestAttribute("loginId") String loginId,
                                                                     @PathVariable("returningRefundHistoryCode") int returningRefundHistoryCode) {
        returningService.completeRefund(loginId, returningRefundHistoryCode);
        return ResponseEntity.ok(new ResponseMessage<>(200, "환불완료 성공", null));
    }
}
