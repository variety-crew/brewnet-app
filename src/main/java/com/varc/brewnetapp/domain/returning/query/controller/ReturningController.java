package com.varc.brewnetapp.domain.returning.query.controller;

import com.varc.brewnetapp.common.ResponseMessage;
import com.varc.brewnetapp.domain.returning.query.aggregate.vo.ReturningDetailVO;
import com.varc.brewnetapp.domain.returning.query.aggregate.vo.ReturningListVO;
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
    @Operation(summary = "[본사] 반품요청 엑셀 데이터(전체 반품내역) 조회 API")
    public ResponseEntity<ResponseMessage<List<ReturningListVO>>> findReturningExcelList() {
        List <ReturningListVO> result = returningService.findAllReturningList();
        return ResponseEntity.ok(new ResponseMessage<>(200, "반품요청 목록 조회 성공", result));
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
                    "3가지 검색 조건과 생성일자 검색은 AND로 함께 필터링 검색 가능")
    public ResponseEntity<ResponseMessage<Page<ReturningListVO>>> searchReturningList(
            @RequestParam(required = false) String searchFilter,
            @RequestParam(required = false) String searchWord,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @PageableDefault(value = 10) Pageable page) {

        Page<ReturningListVO> result = returningService.searchReturningList(searchFilter, searchWord, startDate, endDate, page);

        return ResponseEntity.ok(new ResponseMessage<>(200, "반품요청 목록 검색 성공", result));
    }

    @GetMapping("/{returningCode}")
    @Operation(summary = "[본사] 반품요청 상세조회 API")
    public ResponseEntity<ResponseMessage<ReturningDetailVO>> findReturningBy(@PathVariable("returningCode") Integer returningCode) {

        ReturningDetailVO result = returningService.findReturningDetailBy(returningCode);
        return ResponseEntity.ok(new ResponseMessage<>(200, "반품요청 상세조회 성공", result));
    }

}