package com.varc.brewnetapp.domain.exchange.command.application.controller;

import com.varc.brewnetapp.common.ResponseMessage;
import com.varc.brewnetapp.domain.exchange.command.application.service.ExchangeServiceImpl;
import com.varc.brewnetapp.domain.exchange.command.domain.aggregate.vo.ExchangeApproveReqVO;
import com.varc.brewnetapp.domain.exchange.command.domain.aggregate.vo.ExchangeReqVO;
import com.varc.brewnetapp.exception.InvalidStatusException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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

    @PostMapping("/drafter-approve")
    @Operation(summary = "[본사] 교환 결재신청(기안자) API")
    public ResponseEntity<ResponseMessage<Integer>> approveExchange(@RequestAttribute("loginId") String loginId,
                                                                    @RequestBody ExchangeApproveReqVO exchangeApproveReqVO) {
        exchangeService.approveExchange(loginId, exchangeApproveReqVO);
        return ResponseEntity.ok(new ResponseMessage<>(200, "교환 결재신청 성공", null));
    }
}
