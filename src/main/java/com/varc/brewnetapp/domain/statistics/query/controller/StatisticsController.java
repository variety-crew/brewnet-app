package com.varc.brewnetapp.domain.statistics.query.controller;

import com.varc.brewnetapp.common.ResponseMessage;
import com.varc.brewnetapp.domain.statistics.query.dto.MyWaitApprovalDTO;
import com.varc.brewnetapp.domain.statistics.query.dto.OrderCountPriceDTO;
import com.varc.brewnetapp.domain.statistics.query.dto.OrderStatisticsDTO;
import com.varc.brewnetapp.domain.statistics.query.dto.SafeStockStatisticsDTO;
import com.varc.brewnetapp.domain.statistics.query.dto.newOrderDTO;
import com.varc.brewnetapp.domain.statistics.query.service.StatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "queryStatisticsController")
@RequestMapping("api/v1/hq/statistics")
public class StatisticsController {

    private final StatisticsService statisticsService;

    @Autowired
    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("/order")
    @Operation(summary = "주문 통계 API")
    public ResponseEntity<ResponseMessage<OrderStatisticsDTO>> findOrderStatistics(
        @RequestParam(required = false) String startDate,
        @RequestParam(required = false) String endDate) {

        return ResponseEntity.ok(new ResponseMessage<>(
            200, "주문 통계 조회 성공", statisticsService.findOrderStatistics(startDate, endDate)));
    }

    @GetMapping("/order-quantity-price")
    @Operation(summary = "주문 수량 금액 API")
    public ResponseEntity<ResponseMessage<List<OrderCountPriceDTO>>> findOrderCountPriceStatistics(
        @RequestParam(required = false) String yearMonth) {

        return ResponseEntity.ok(new ResponseMessage<>(
            200, "일자 별 주문 수량 금액 조회 성공", statisticsService.findOrderCountPriceStatistics(yearMonth)));
    }

    @GetMapping("/safe-stock")
    @Operation(summary = "안전 재고 위험 알림 API")
    public ResponseEntity<ResponseMessage<Page<SafeStockStatisticsDTO>>> findSafeStock(
        @PageableDefault(size = 10, page = 0) Pageable page) {

        return ResponseEntity.ok(new ResponseMessage<>(
            200, "안전 재고 위험 알림 통계 조회 성공", statisticsService.findSafeStock(page)));
    }

    @GetMapping("/new-order")
    @Operation(summary = "신규 주문 목록 API")
    public ResponseEntity<ResponseMessage<Page<newOrderDTO>>> findNewOrder(
        @PageableDefault(size = 10, page = 0) Pageable page) {

        return ResponseEntity.ok(new ResponseMessage<>(
            200, "신규 주문 목록 조회 성공", statisticsService.findNewOrder(page)));
    }

    @GetMapping("/my-wait-approval")
    @Operation(summary = "나의 결제 대기 목록 API")
    public ResponseEntity<ResponseMessage<Page<MyWaitApprovalDTO>>> findMyWaitApproval(
        @PageableDefault(size = 10, page = 0) Pageable page,
        @RequestHeader("Authorization") String accessToken) {

        return ResponseEntity.ok(new ResponseMessage<>(
            200, "나의 결재 대기 목록 조회 성공", statisticsService.findMyWaitApproval(page, accessToken)));
    }
}
