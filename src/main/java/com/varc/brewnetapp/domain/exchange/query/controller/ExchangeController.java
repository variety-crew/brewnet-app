package com.varc.brewnetapp.domain.exchange.query.controller;

import com.varc.brewnetapp.common.ResponseMessage;
import com.varc.brewnetapp.domain.exchange.query.aggregate.vo.*;
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
            @PageableDefault(value = 10) Pageable page) {

        // 페이지네이션
        Page<ExchangeListResponseVO> result = exchangeService.findExchangeList(paramMap, page);
        return ResponseEntity.ok(new ResponseMessage<>(200, "교환요청 목록 조회 성공", result));
    }

    @PostMapping("/search")
    @Operation(summary = "[본사] 교환요청 목록 검색 API",
            description = "searchFilter에 들어갈 수 있는 값은 exchangeCode(교환번호), franchiseName(교환지점), managerName(교환지점) 3가지<br>" +
                    "생성일자로 검색하고 싶은 경우 startDate(검색시작일), endDate(검색마지막일)을 입력<br>" +
                    "3가지 검색 조건과 생성일자 검색은 AND로 함께 필터링 검색 가능")
    public ResponseEntity<ResponseMessage<Page<ExchangeListResponseVO>>> searchExchangeList(
            @RequestParam(required = false) String searchFilter,
            @RequestParam(required = false) String searchWord,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam Map<String, Object> paramMap,
            @PageableDefault(value = 10) Pageable page) {

        Page<ExchangeListResponseVO> result = exchangeService.searchExchangeList(searchFilter, searchWord, startDate, endDate, paramMap, page);

        return ResponseEntity.ok(new ResponseMessage<>(200, "교환요청 목록 검색 성공", result));
    }

    @PostMapping("/{code}")
    @Operation(summary = "[본사] 교환요청 상세조회 API")
    public ResponseEntity<ResponseMessage<ExchangeDetailResponseVO>> findExchangeBy(@PathVariable("code") Integer exchangeCode) {

        ExchangeDetailResponseVO result = exchangeService.findExchangeDetailBy(exchangeCode);
        return ResponseEntity.ok(new ResponseMessage<>(200, "교환요청 상세조회 성공", result));
    }

    @PostMapping("/history")
    @Operation(summary = "[본사] 타부서 교환처리내역 목록 조회 API")
    public ResponseEntity<ResponseMessage<Page<ExchangeHistoryResponseVO>>> findExchangeHistoryList(@RequestParam Map<String, Object> paramMap,
                                                                                                    @PageableDefault(value = 10) Pageable page) {
        Page<ExchangeHistoryResponseVO> result = exchangeService.findExchangeHistoryList(paramMap, page);
        return ResponseEntity.ok(new ResponseMessage<>(200, "타부서 교환처리내역 목록조회 성공", result));
    }

    @PostMapping("/history/{code}")
    @Operation(summary = "[본사] 타부서 교환처리내역 상세조회 API")
    public ResponseEntity<ResponseMessage<ExchangeHistoryDetailResponseVO>> findExchangeHistoryBy(@PathVariable("code") Integer exchangeStockHistoryCode) {

        ExchangeHistoryDetailResponseVO result = exchangeService.findExchangeHistoryDetailBy(exchangeStockHistoryCode);
        return ResponseEntity.ok(new ResponseMessage<>(200, "타부서 교환처리내역 상세조회 성공", result));
    }
}
