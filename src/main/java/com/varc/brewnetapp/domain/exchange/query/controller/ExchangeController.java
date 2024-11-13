package com.varc.brewnetapp.domain.exchange.query.controller;

import com.varc.brewnetapp.common.ResponseMessage;
import com.varc.brewnetapp.domain.exchange.query.aggregate.vo.ExchangeDetailResponseVO;
import com.varc.brewnetapp.domain.exchange.query.aggregate.vo.ExchangeHistoryResponseVO;
import com.varc.brewnetapp.domain.exchange.query.aggregate.vo.ExchangeListResponseVO;
import com.varc.brewnetapp.domain.exchange.query.service.ExchangeServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController("ExchangeControllerQuery")
@RequiredArgsConstructor
@RequestMapping("/api/v1/exchange")
@Slf4j
public class ExchangeController {

    private final ExchangeServiceImpl exchangeService;

    @PostMapping("")
    @Operation(summary = "[본사] 교환요청 목록 조회 API")
    public ResponseEntity<ResponseMessage<Page<ExchangeListResponseVO>>> findExchangeList(
            @RequestParam Map<String, Object> paramMap,
            @PageableDefault(value = 3) Pageable page) {

        // 페이지네이션
        Page<ExchangeListResponseVO> result = exchangeService.findExchangeList(paramMap, page);
        return ResponseEntity.ok(new ResponseMessage<>(200, "교환요청 목록 조회 성공", result));
    }

    @PostMapping("/{exchangeCode}")
    @Operation(summary = "[본사] 교환요청 상세조회 API")
    public ResponseEntity<ResponseMessage<ExchangeDetailResponseVO>> findExchangeBy(@PathVariable("exchangeCode") Integer exchangeCode) {

        ExchangeDetailResponseVO result = exchangeService.findExchangeDetailBy(exchangeCode);
        return ResponseEntity.ok(new ResponseMessage<>(200, "교환요청 상세조회 성공", result));
    }

    @PostMapping("/history")
    @Operation(summary = "[본사] 타부서 교환 처리 내역 확인 API")
    public ResponseEntity<ResponseMessage<Page<ExchangeHistoryResponseVO>>> findExchangeHistoryList(@RequestParam Map<String, Object> paramMap,
                                                                                                    @PageableDefault(value = 3) Pageable page) {

        Page<ExchangeHistoryResponseVO> result = exchangeService.findExchangeHistoryList(paramMap, page);
        return ResponseEntity.ok(new ResponseMessage<>(200, "타부서 교환 처리 내역 확인 성공", result));
    }
}
