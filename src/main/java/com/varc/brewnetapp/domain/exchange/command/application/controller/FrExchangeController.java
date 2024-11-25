package com.varc.brewnetapp.domain.exchange.command.application.controller;

import com.varc.brewnetapp.common.ResponseMessage;
import com.varc.brewnetapp.domain.exchange.command.application.service.ExchangeServiceImpl;
import com.varc.brewnetapp.domain.exchange.command.domain.aggregate.vo.ExchangeReqVO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("FrExchangeControllerCommand")
@RequiredArgsConstructor
@RequestMapping("/api/v1/franchise/exchange")
@Slf4j
public class FrExchangeController {

    private final ExchangeServiceImpl exchangeService;

    @PostMapping("/")
    @Operation(summary = "[가맹점] 교환신청 API")
    public ResponseEntity<ResponseMessage<Integer>> registExchange(@RequestAttribute("loginId") String loginId,
                                                                         @RequestBody ExchangeReqVO exchangeReqVO) {
        int exchangeCode = exchangeService.franCreateExchange(loginId, exchangeReqVO);
        return ResponseEntity.ok(new ResponseMessage<>(200, "교환신청 성공", exchangeCode));
    }

    @PostMapping("/cancel/{exchangeCode}")
    @Operation(summary = "[가맹점] 교환취소 API")
    public ResponseEntity<ResponseMessage<Integer>> cancelExchange(@RequestAttribute("loginId") String loginId,
                                                                   @PathVariable("exchangeCode") Integer exchangeCode) {
        exchangeService.franCancelExchange(loginId, exchangeCode);
        return ResponseEntity.ok(new ResponseMessage<>(200, "교환취소 성공", exchangeCode));
    }
}
