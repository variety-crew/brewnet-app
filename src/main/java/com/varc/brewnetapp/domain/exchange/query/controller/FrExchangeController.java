package com.varc.brewnetapp.domain.exchange.query.controller;

import com.varc.brewnetapp.common.ResponseMessage;
import com.varc.brewnetapp.domain.exchange.query.aggregate.vo.FranExchangeDetailVO;
import com.varc.brewnetapp.domain.exchange.query.aggregate.vo.FranExchangeListVO;
import com.varc.brewnetapp.domain.exchange.query.aggregate.vo.FranExchangeStatusVO;
import com.varc.brewnetapp.domain.exchange.query.service.ExchangeServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/* [임시] 가맹점용 교환 컨트롤러 */
@RestController("FrExchangeControllerQuery")
@RequiredArgsConstructor
@RequestMapping("/api/v1/franchise/exchange")
@Slf4j
public class FrExchangeController {

    private final ExchangeServiceImpl exchangeService;

    @GetMapping("")
    @Operation(summary = "[가맹점] 교환요청 목록조회 API")
    public ResponseEntity<ResponseMessage<Page<FranExchangeListVO>>> findFranExchangeList(@RequestAttribute("loginId") String loginId,
                                                                                          @PageableDefault(value = 10) Pageable page) {
        Page<FranExchangeListVO> result = exchangeService.findFranExchangeList(loginId, page);
        return ResponseEntity.ok(new ResponseMessage<>(200, "가맹점 교환요청 목록조회 성공", result));
    }

    @GetMapping("/search")
    @Operation(summary = "[가맹점] 교환요청 목록 검색 API",
            description = "searchFilter에 들어갈 수 있는 값은 exchangeCode(교환번호), itemName(품목명) 2가지<br>" +
                    "생성일자로 검색하고 싶은 경우 startDate(검색시작일), endDate(검색마지막일)을 입력<br>" +
                    "2가지 검색 조건과 생성일자 검색은 AND로 함께 필터링 검색 가능")
    public ResponseEntity<ResponseMessage<Page<FranExchangeListVO>>> searchFranExchangeList(
            @RequestAttribute("loginId") String loginId,
            @RequestParam(required = false) String searchFilter,
            @RequestParam(required = false) String searchWord,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @PageableDefault(value = 10) Pageable page) {

        Page<FranExchangeListVO> result = exchangeService.searchFranExchangeList(loginId, searchFilter, searchWord, startDate, endDate, page);

        return ResponseEntity.ok(new ResponseMessage<>(200, "가맹점 교환요청 목록 검색 성공", result));
    }

    @GetMapping("/{exchangeCode}")
    @Operation(summary = "[가맹점] 교환요청 상세조회 API")
    public ResponseEntity<ResponseMessage<FranExchangeDetailVO>> findFranExchangeDetail(@RequestAttribute("loginId") String loginId,
                                                                                        @PathVariable("exchangeCode") Integer exchangeCode) {

        FranExchangeDetailVO result = exchangeService.findFranExchangeDetailBy(loginId, exchangeCode);
        return ResponseEntity.ok(new ResponseMessage<>(200, "가맹점 교환요청 상세조회 성공", result));
    }

    @GetMapping("/status/{exchangeCode}")
    @Operation(summary = "[가맹점] 교환요청 상세조회 - 교환상태조회 API")
    public ResponseEntity<ResponseMessage<List<FranExchangeStatusVO>>> findFranExchangeStatus(@RequestAttribute("loginId") String loginId,
                                                                                              @PathVariable("exchangeCode") Integer exchangeCode) {

        List<FranExchangeStatusVO> result = exchangeService.findFranExchangeStatusBy(loginId, exchangeCode);
        return ResponseEntity.ok(new ResponseMessage<>(200, "가맹점 교환요청 상세조회 성공", result));
    }

}
