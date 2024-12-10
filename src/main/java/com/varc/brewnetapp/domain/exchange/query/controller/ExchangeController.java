package com.varc.brewnetapp.domain.exchange.query.controller;

import com.varc.brewnetapp.common.ResponseMessage;
import com.varc.brewnetapp.domain.exchange.query.aggregate.vo.*;
import com.varc.brewnetapp.domain.exchange.query.service.ExchangeServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController("ExchangeControllerQuery")
@RequiredArgsConstructor
@RequestMapping("/api/v1/hq/exchange")
@Slf4j
public class ExchangeController {

    private final ExchangeServiceImpl exchangeService;

    @GetMapping("")
    @Operation(summary = "[본사] 교환요청 목록 조회 API")
    public ResponseEntity<ResponseMessage<Page<ExchangeListVO>>> findExchangeList(
            @PageableDefault(value = 10) Pageable page) {

        // 페이지네이션
        Page<ExchangeListVO> result = exchangeService.findExchangeList(page);
        return ResponseEntity.ok(new ResponseMessage<>(200, "교환요청 목록 조회 성공", result));
    }

    @GetMapping("/excel-data")
    @Operation(summary = "[본사] 교환요청 엑셀 데이터 조회 API",
            description = "searchFilter에 들어갈 수 있는 값은 exchangeCode(교환번호), franchiseName(교환지점), managerName(교환지점) 3가지<br>" +
                    "생성일자로 검색하고 싶은 경우 startDate(검색시작일), endDate(검색마지막일)을 입력<br>" +
                    "3가지 검색 조건과 생성일자 검색은 AND로 함께 필터링 검색 가능")
    public ResponseEntity<ResponseMessage<List<ExchangeListVO>>> findExcelExchangeList(
            @RequestParam(required = false) String searchFilter,
            @RequestParam(required = false) String searchWord,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        List<ExchangeListVO> result = exchangeService.findExcelExchangeList(searchFilter, searchWord, startDate, endDate);
        return ResponseEntity.ok(new ResponseMessage<>(200, "교환요청 엑셀 데이터 조회 성공", result));
    }

    @GetMapping("/status-requested")
    @Operation(summary = "[본사] 미결재된 교환요청 목록 조회 API")
    public ResponseEntity<ResponseMessage<Page<ExchangeListVO>>> findRequestedExchangeList(
            @PageableDefault(value = 10) Pageable page) {

        // 페이지네이션
        Page<ExchangeListVO> result = exchangeService.findRequestedExchangeList(page);
        return ResponseEntity.ok(new ResponseMessage<>(200, "미결재된 교환요청 목록 조회 성공", result));
    }

    @GetMapping("/search")
    @Operation(summary = "[본사] 교환요청 목록 검색 API",
            description = "searchFilter에 들어갈 수 있는 값은 exchangeCode(교환번호), franchiseName(교환지점), managerName(교환지점) 3가지<br>" +
                    "생성일자로 검색하고 싶은 경우 startDate(검색시작일), endDate(검색마지막일)을 입력<br>" +
                    "3가지 검색 조건과 생성일자 검색은 AND로 함께 필터링 검색 가능" +
                    "결재여부는 boolean으로 false(전체목록), true(미결재목록)")
    public ResponseEntity<ResponseMessage<Page<ExchangeListVO>>> searchExchangeList(
            @RequestParam(required = false) String searchFilter,
            @RequestParam(required = false) String searchWord,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) boolean getConfirmed,
            @PageableDefault(value = 10) Pageable page) {

        Page<ExchangeListVO> result = exchangeService.searchExchangeList(searchFilter, searchWord, startDate, endDate, getConfirmed, page);

        return ResponseEntity.ok(new ResponseMessage<>(200, "교환요청 목록 검색 성공", result));
    }

    @GetMapping("/{exchangeCode}")
    @Operation(summary = "[본사] 교환요청 상세조회 API")
    public ResponseEntity<ResponseMessage<ExchangeDetailVO>> findExchangeBy(@PathVariable("exchangeCode") Integer exchangeCode) {

        ExchangeDetailVO result = exchangeService.findExchangeDetailBy(exchangeCode);
        return ResponseEntity.ok(new ResponseMessage<>(200, "교환요청 상세조회 성공", result));
    }

    @GetMapping("/other")
    @Operation(summary = "[본사] 타부서 교환처리내역 목록 조회/검색 API",
            description = "조회 시에는 searchFilter, 생성일자에 아무 값도 필요하지 않음<br>" +
                    "searchFilter에 들어갈 수 있는 값은 code(처리번호), manager(처리담당자), exchangeCode(교환번호), exchangeManager(교환담당자) 4가지<br>" +
                    "생성일자로 검색하고 싶은 경우 startDate(검색시작일), endDate(검색마지막일)을 입력<br>" +
                    "4가지 검색 조건과 생성일자 검색은 AND로 함께 필터링 검색 가능")
    public ResponseEntity<ResponseMessage<Page<ExchangeHistoryVO>>> findExchangeHistoryList(
            @RequestParam(required = false) String searchFilter,
            @RequestParam(required = false) String searchWord,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @PageableDefault(value = 10) Pageable page) {
        Page<ExchangeHistoryVO> result = exchangeService.findExchangeHistoryList(searchFilter, searchWord, startDate, endDate, page);
        return ResponseEntity.ok(new ResponseMessage<>(200, "타부서 교환처리내역 목록조회 성공", result));
    }

//    @GetMapping("/other/search")
//    @Operation(summary = "[본사] 타부서 교환처리내역 목록 검색 API",
//            description = "searchFilter에 들어갈 수 있는 값은 code(처리번호), manager(처리담당자), exchangeCode(교환번호), exchangeManager(교환담당자) 4가지<br>" +
//                    "생성일자로 검색하고 싶은 경우 startDate(검색시작일), endDate(검색마지막일)을 입력<br>" +
//                    "4가지 검색 조건과 생성일자 검색은 AND로 함께 필터링 검색 가능")
//    public ResponseEntity<ResponseMessage<Page<ExchangeHistoryVO>>> searchExchangeHistoryList(
//            @RequestParam(required = false) String searchFilter,
//            @RequestParam(required = false) String searchWord,
//            @RequestParam(required = false) String startDate,
//            @RequestParam(required = false) String endDate,
//            @PageableDefault(value = 10) Pageable page) {
//
//        Page<ExchangeHistoryVO> result = exchangeService.searchExchangeHistoryList(searchFilter, searchWord, startDate, endDate, page);
//
//        return ResponseEntity.ok(new ResponseMessage<>(200, "교환요청 목록 검색 성공", result));
//    }

    @GetMapping("/other/{exchangeStockHistoryCode}")
    @Operation(summary = "[본사] 타부서 교환처리내역 상세조회 API")
    public ResponseEntity<ResponseMessage<ExchangeHistoryDetailVO>> findExchangeHistoryBy(@PathVariable("exchangeStockHistoryCode") Integer exchangeStockHistoryCode) {

        ExchangeHistoryDetailVO result = exchangeService.findExchangeHistoryDetailBy(exchangeStockHistoryCode);
        return ResponseEntity.ok(new ResponseMessage<>(200, "타부서 교환처리내역 상세조회 성공", result));
    }

    @GetMapping("/approver/{exchangeCode}")
    @Operation(summary = "[본사] 교환요청 상세조회 - 결재진행상태 API")
    public ResponseEntity<ResponseMessage<List<ExchangeApproverVO>>> findExchangeApprover(@PathVariable("exchangeCode") Integer exchangeCode) {

        List<ExchangeApproverVO> result = exchangeService.findExchangeApprover(exchangeCode);
        return ResponseEntity.ok(new ResponseMessage<>(200, "교환 결재진행상태 조회 성공", result));
    }

//    @GetMapping("/excel")
//    @Operation(summary = "[본사] 교환목록 엑셀출력 API")
//    public ResponseEntity<ResponseMessage<Void>> exportExchangeExcel(HttpServletResponse response) throws NumberFormatException, IOException {
//
//        Workbook wb = exchangeService.exportExchangeExcel();
//
//        // 컨텐츠 타입, 파일명 지정
//        response.setContentType("ms-vnd/excel");
//        response.setHeader("Content-Disposition", "attachment;filename=exchangeList.xlsx");
//
//        // 엑셀파일 저장
//        wb.write(response.getOutputStream());
//        wb.close();
//
//        return ResponseEntity.ok(new ResponseMessage<>(200, "교환 결재진행상태 조회 성공", null));
//    }
}
