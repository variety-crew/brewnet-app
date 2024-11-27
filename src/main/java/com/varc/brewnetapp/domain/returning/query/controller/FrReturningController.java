package com.varc.brewnetapp.domain.returning.query.controller;

import com.varc.brewnetapp.common.ResponseMessage;
import com.varc.brewnetapp.domain.exchange.query.aggregate.vo.FranExchangeStatusVO;
import com.varc.brewnetapp.domain.returning.query.aggregate.vo.FranReturningDetailVO;
import com.varc.brewnetapp.domain.returning.query.aggregate.vo.FranReturningStatusVO;
import com.varc.brewnetapp.domain.returning.query.service.ReturningServiceImpl;
import com.varc.brewnetapp.domain.returning.query.aggregate.vo.FranReturningListVO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("FrReturningControllerQuery")
@RequiredArgsConstructor
@RequestMapping("/api/v1/franchise/return")
@Slf4j
public class FrReturningController {
    private final ReturningServiceImpl returningService;

    @GetMapping("")
    @Operation(summary = "[가맹점] 반품요청 목록조회 API")
        public ResponseEntity<ResponseMessage<Page<FranReturningListVO>>> findFranReturningList(@RequestAttribute("loginId") String loginId,
                                                                                                @PageableDefault(value = 10) Pageable page) {
        Page<FranReturningListVO> result = returningService.findFranReturningList(loginId, page);
        return ResponseEntity.ok(new ResponseMessage<>(200, "가맹점 반품요청 목록조회 성공", result));
    }

    @GetMapping("/search")
    @Operation(summary = "[가맹점] 반품요청 목록 검색 API",
            description = "searchFilter에 들어갈 수 있는 값은 returningCode(반품번호), itemName(품목명) 2가지<br>" +
                    "생성일자로 검색하고 싶은 경우 startDate(검색시작일), endDate(검색마지막일)을 입력<br>" +
                    "2가지 검색 조건과 생성일자 검색은 AND로 함께 필터링 검색 가능")
    public ResponseEntity<ResponseMessage<Page<FranReturningListVO>>> searchFranReturningList(
            @RequestAttribute("loginId") String loginId,
            @RequestParam(required = false) String searchFilter,
            @RequestParam(required = false) String searchWord,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @PageableDefault(value = 10) Pageable page) {

        Page<FranReturningListVO> result = returningService.searchFranReturningList(loginId, searchFilter, searchWord, startDate, endDate, page);

        return ResponseEntity.ok(new ResponseMessage<>(200, "가맹점 반품요청 목록 검색 성공", result));
    }

    @GetMapping("/{returningCode}")
    @Operation(summary = "[가맹점] 반품요청 상세조회 API")
    public ResponseEntity<ResponseMessage<FranReturningDetailVO>> findFranReturningDetail(@RequestAttribute("loginId") String loginId,
                                                                                          @PathVariable("returningCode") Integer returningCode) {

        FranReturningDetailVO result = returningService.findFranReturningDetailBy(loginId, returningCode);
        return ResponseEntity.ok(new ResponseMessage<>(200, "가맹점 반품요청 상세조회 성공", result));
    }

    @GetMapping("/status/{returningCode}")
    @Operation(summary = "[가맹점] 반품요청 상세조회 - 반품상태조회 API")
    public ResponseEntity<ResponseMessage<List<FranReturningStatusVO>>> findFranReturningStatus(@RequestAttribute("loginId") String loginId,
                                                                                                @PathVariable("returningCode") Integer returningCode) {

        List<FranReturningStatusVO> result = returningService.findFranReturningCodeStatusBy(loginId, returningCode);
        return ResponseEntity.ok(new ResponseMessage<>(200, "가맹점 반품요청 상세조회 성공", result));
    }

    @GetMapping("/available-orders")
    @Operation(summary = "[가맹점] 반품신청 - 1. 반품신청 가능한 주문 목록 조회 API")
    public ResponseEntity<ResponseMessage<List<Integer>>> findFranAvailableReturningBy(@RequestAttribute("loginId") String loginId) {
        List<Integer> result = returningService.findFranAvailableReturningBy(loginId);
        return ResponseEntity.ok(new ResponseMessage<>(200, "가맹점 반품신청 가능한 주문 목록 조회 성공", result));
    }
//
//    @GetMapping("/available-items/{orderCode}")
//    @Operation(summary = "[가맹점] 교환신청 - 2. 교환신청할 주문의 상품목록 조회 API")
//    public ResponseEntity<ResponseMessage<List<FranExchangeItemVO>>> findFranAvailableExchangeItemBy(@RequestAttribute("loginId") String loginId,
//                                                                                                     @PathVariable("orderCode") int orderCode) {
//        List<FranExchangeItemVO> result = returningService.findFranAvailableExchangeItemBy(loginId, orderCode);
//        return ResponseEntity.ok(new ResponseMessage<>(200, "가맹점 교환신청할 주문의 상품목록 조회 성공", result));
//    }
}
