package com.varc.brewnetapp.domain.returning.query.controller;

import com.varc.brewnetapp.common.ResponseMessage;
import com.varc.brewnetapp.domain.returning.query.aggregate.vo.*;
import com.varc.brewnetapp.domain.returning.query.service.ReturningServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("ReturningControllerQuery")
@RequiredArgsConstructor
@RequestMapping("/api/v1/hq/return")
@Slf4j
public class ReturningController {

    private final ReturningServiceImpl returningService;

    @GetMapping("")
    @Operation(summary = "[본사] 반품요청 목록 조회 API")
    public ResponseEntity<ResponseMessage<Page<ReturningListVO>>> findReturningList(
            @PageableDefault(value = 10) Pageable page) {

        // 페이지네이션
        Page<ReturningListVO> result = returningService.findReturningList(page);
        return ResponseEntity.ok(new ResponseMessage<>(200, "반품요청 목록 조회 성공", result));
    }

    @GetMapping("/excel-data")
    @Operation(summary = "[본사] 반품요청 엑셀 데이터 조회 API",
            description = "searchFilter에 들어갈 수 있는 값은 returningCode(반품번호), franchiseName(반품지점), managerName(반품지점) 3가지<br>" +
                    "생성일자로 검색하고 싶은 경우 startDate(검색시작일), endDate(검색마지막일)을 입력<br>" +
                    "3가지 검색 조건과 생성일자 검색은 AND로 함께 필터링 검색 가능")
    public ResponseEntity<ResponseMessage<List<ReturningListVO>>> findExcelReturningList(
            @RequestParam(required = false) String searchFilter,
            @RequestParam(required = false) String searchWord,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        List<ReturningListVO> result = returningService.findExcelReturningList(searchFilter, searchWord, startDate, endDate);
        return ResponseEntity.ok(new ResponseMessage<>(200, "반품요청 엑셀 데이터 조회 성공", result));
    }

    @GetMapping("/status-requested")
    @Operation(summary = "[본사] 미결재된 반품요청 목록 조회 API")
    public ResponseEntity<ResponseMessage<Page<ReturningListVO>>> findRequestedReturningList(
            @PageableDefault(value = 10) Pageable page) {

        // 페이지네이션
        Page<ReturningListVO> result = returningService.findRequestedReturningList(page);
        return ResponseEntity.ok(new ResponseMessage<>(200, "미결재된 반품요청 목록 조회 성공", result));
    }

    @GetMapping("/search")
    @Operation(summary = "[본사] 반품요청 목록 검색 API",
            description = "searchFilter에 들어갈 수 있는 값은 returningCode(반품번호), franchiseName(반품지점), managerName(반품담당자) 3가지<br>" +
                    "생성일자로 검색하고 싶은 경우 startDate(검색시작일), endDate(검색마지막일)을 입력<br>" +
                    "3가지 검색 조건과 생성일자 검색은 AND로 함께 필터링 검색 가능<br>" +
                    "결재여부는 boolean으로 false(전체목록), true(미결재목록) 선택")
    public ResponseEntity<ResponseMessage<Page<ReturningListVO>>> searchReturningList(
            @RequestParam(required = false) String searchFilter,
            @RequestParam(required = false) String searchWord,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) boolean getConfirmed,
            @PageableDefault(value = 10) Pageable page) {

        Page<ReturningListVO> result = returningService.searchReturningList(searchFilter, searchWord, startDate, endDate, getConfirmed, page);

        return ResponseEntity.ok(new ResponseMessage<>(200, "반품요청 목록 검색 성공", result));
    }

    @GetMapping("/{returningCode}")
    @Operation(summary = "[본사] 반품요청 상세조회 API")
    public ResponseEntity<ResponseMessage<ReturningDetailVO>> findReturningBy(@PathVariable("returningCode") Integer returningCode) {

        ReturningDetailVO result = returningService.findReturningDetailBy(returningCode);
        return ResponseEntity.ok(new ResponseMessage<>(200, "반품요청 상세조회 성공", result));
    }

    @GetMapping("/other/return")
    @Operation(summary = "[본사] 타부서 반품처리내역 목록 조회/검색 API",
            description = "조회 시에는 searchFilter, 생성일자에 아무 값도 필요하지 않음<br>" +
                    "검색 시 searchFilter에 들어갈 수 있는 값은 code(반품처리번호), manager(반품처리담당자), returningCode(반품번호), returningManager(반품담당자) 4가지<br>" +
                    "생성일자로 검색하고 싶은 경우 startDate(검색시작일), endDate(검색마지막일)을 입력<br>" +
                    "4가지 검색 조건과 생성일자 검색은 AND로 함께 필터링 검색 가능")
    public ResponseEntity<ResponseMessage<Page<ReturningHistoryVO>>> findReturningHistoryList(
            @RequestParam(required = false) String searchFilter,
            @RequestParam(required = false) String searchWord,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @PageableDefault(value = 10) Pageable page) {
        Page<ReturningHistoryVO> result = returningService.findReturningHistoryList(searchFilter, searchWord, startDate, endDate, page);
        return ResponseEntity.ok(new ResponseMessage<>(200, "타부서 반품처리내역 목록조회/검색 성공", result));
    }

    @GetMapping("/other/return/{returningStockHistoryCode}")
    @Operation(summary = "[본사] 타부서 반품처리내역 상세조회 API")
    public ResponseEntity<ResponseMessage<ReturningHistoryDetailVO>> findReturningHistoryBy(@PathVariable("returningStockHistoryCode") Integer returningStockHistoryCode) {

        ReturningHistoryDetailVO result = returningService.findReturningHistoryDetailBy(returningStockHistoryCode);
        return ResponseEntity.ok(new ResponseMessage<>(200, "타부서 반품처리내역 상세조회 성공", result));
    }

    @GetMapping("/other/refund")
    @Operation(summary = "[본사] 타부서 환불처리내역 목록 조회/검색 API",
            description = "조회 시에는 searchFilter, 생성일자에 아무 값도 필요하지 않음<br>" +
                    "검색 시 searchFilter에 들어갈 수 있는 값은 code(환불처리번호), manager(환불처리담당자), returningCode(반품번호), returningManager(반품담당자) 4가지<br>" +
                    "생성일자로 검색하고 싶은 경우 startDate(검색시작일), endDate(검색마지막일)을 입력<br>" +
                    "4가지 검색 조건과 생성일자 검색은 AND로 함께 필터링 검색 가능")
    public ResponseEntity<ResponseMessage<Page<RefundHistoryVO>>> findRefundHistoryList(
            @RequestParam(required = false) String searchFilter,
            @RequestParam(required = false) String searchWord,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @PageableDefault(value = 10) Pageable page) {
        Page<RefundHistoryVO> result = returningService.findRefundHistoryList(searchFilter, searchWord, startDate, endDate, page);
        return ResponseEntity.ok(new ResponseMessage<>(200, "타부서 환불처리내역 목록조회/검색 성공", result));
    }

    @GetMapping("/other/refund/{returningRefundHistoryCode}")
    @Operation(summary = "[본사] 타부서 환불처리내역 상세조회 API")
    public ResponseEntity<ResponseMessage<RefundHistoryDetailVO>> findRefundHistoryBy(@PathVariable("returningRefundHistoryCode") Integer returningRefundHistoryCode) {

        RefundHistoryDetailVO result = returningService.findRefundHistoryDetailBy(returningRefundHistoryCode);
        return ResponseEntity.ok(new ResponseMessage<>(200, "타부서 환불처리내역 상세조회 성공", result));
    }

    @GetMapping("/approver/{returningCode}")
    @Operation(summary = "[본사] 반품요청 상세조회 - 결재진행상태 API")
    public ResponseEntity<ResponseMessage<List<ReturningApproverVO>>> findReturningApprover(@PathVariable("returningCode") Integer returningCode) {
        List<ReturningApproverVO> result = returningService.findReturningApprover(returningCode);
        return ResponseEntity.ok(new ResponseMessage<>(200, "반품 결재진행상태 조회 성공", result));
    }

}