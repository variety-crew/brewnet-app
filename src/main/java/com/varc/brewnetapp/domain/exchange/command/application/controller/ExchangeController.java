package com.varc.brewnetapp.domain.exchange.command.application.controller;

import com.varc.brewnetapp.common.ResponseMessage;
import com.varc.brewnetapp.domain.exchange.command.application.service.ExchangeServiceImpl;
import com.varc.brewnetapp.domain.exchange.command.domain.aggregate.vo.ExchangeReqVO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return ResponseEntity.ok(new ResponseMessage<>(200, "타부서 교환처리내역 목록조회 성공", null));
    }

}
