package com.varc.brewnetapp.domain.exchange.command.application.controller;

import com.varc.brewnetapp.common.ResponseMessage;
import com.varc.brewnetapp.domain.exchange.command.application.service.ExchangeServiceImpl;
import com.varc.brewnetapp.domain.exchange.command.domain.aggregate.vo.ExchangeApproveReqVO;
import com.varc.brewnetapp.domain.exchange.command.domain.aggregate.vo.ExchangeReqVO;
import com.varc.brewnetapp.exception.InvalidStatusException;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("ExchangeControllerCommand")
@RequiredArgsConstructor
@RequestMapping("/api/v1/exchange")
@Slf4j
public class ExchangeController {

    private final ExchangeServiceImpl exchangeService;

    @PostMapping("/")
    @Operation(summary = "[가맹점] 교환신청 API")
    public ResponseEntity<ResponseMessage<ExchangeReqVO>> registExchange(@RequestBody ExchangeReqVO exchangeReqVO) {
        exchangeService.createExchange(exchangeReqVO);
        return ResponseEntity.ok(new ResponseMessage<>(200, "교환신청 성공", null));
    }

    @PostMapping("/cancel/{exchangeCode}")
    @Operation(summary = "[가맹점] 교환취소 API")
    public ResponseEntity<ResponseMessage<Integer>> cancelExchange(@PathVariable("exchangeCode") Integer exchangeCode) {
        exchangeService.cancelExchange(exchangeCode);
        return ResponseEntity.ok(new ResponseMessage<>(200, "교환취소 성공", exchangeCode));
    }

    // api url 수정 필요
    @PostMapping("/drafter-approve/{memberCode}")
    @Operation(summary = "[본사] 교환 결재신청(기안자) API")
    public ResponseEntity<ResponseMessage<Integer>> approveExchange(@PathVariable("memberCode") Integer memberCode, @RequestBody ExchangeApproveReqVO exchangeApproveReqVO) {
        exchangeService.approveExchange(memberCode, exchangeApproveReqVO);
        return ResponseEntity.ok(new ResponseMessage<>(200, "교환 결재신청 성공", null));
    }
}
