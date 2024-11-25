package com.varc.brewnetapp.domain.exchange.command.application.controller;

import com.varc.brewnetapp.common.ResponseMessage;
import com.varc.brewnetapp.domain.exchange.command.application.service.ExchangeServiceImpl;
import com.varc.brewnetapp.domain.exchange.command.domain.aggregate.vo.ExchangeDrafterApproveReqVO;
import com.varc.brewnetapp.domain.exchange.command.domain.aggregate.vo.ExchangeManagerApproveReqVO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("ExchangeControllerCommand")
@RequiredArgsConstructor
@RequestMapping("/api/v1/hq/exchange")
@Slf4j
public class ExchangeController {

    private final ExchangeServiceImpl exchangeService;

    @PostMapping("{exchangeCode}/drafter-approve")
    @Operation(summary = "[본사] 교환 결재신청(기안자) API")
    public ResponseEntity<ResponseMessage<Integer>> drafterApproveExchange(@RequestAttribute("loginId") String loginId,
                                                                           @PathVariable("exchangeCode") int exchangeCode,
                                                                           @RequestBody ExchangeDrafterApproveReqVO exchangeApproveReqVO) {
        exchangeService.drafterExchange(loginId, exchangeCode, exchangeApproveReqVO);
        return ResponseEntity.ok(new ResponseMessage<>(200, "교환 결재신청 성공", null));
    }

    @PostMapping("{exchangeCode}/manager-approve")
    @Operation(summary = "[본사] 교환 결재승인(결재자) API")
    public ResponseEntity<ResponseMessage<Integer>> approveExchange(@RequestAttribute("loginId") String loginId,
                                                                    @PathVariable("exchangeCode") int exchangeCode,
                                                                    @RequestBody ExchangeManagerApproveReqVO exchangeApproveReqVO) {
        exchangeService.managerExchange(loginId, exchangeCode, exchangeApproveReqVO);
        return ResponseEntity.ok(new ResponseMessage<>(200, "교환 결재신청 성공", null));
    }
}
