package com.varc.brewnetapp.domain.order.query.controller;

import com.varc.brewnetapp.common.ResponseMessage;

import com.varc.brewnetapp.domain.order.query.dto.HQOrderDTO;
import com.varc.brewnetapp.domain.order.query.dto.OrderRequestDTO;
import com.varc.brewnetapp.domain.order.query.dto.OrderResponseDTO;
import com.varc.brewnetapp.domain.order.query.dto.OrderStatusHistory;
import com.varc.brewnetapp.domain.order.query.service.OrderQueryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/hq/orders")
public class HQOrderQueryController {
    private final OrderQueryService orderQueryService;

    @Autowired
    public HQOrderQueryController(OrderQueryService orderQueryService) {
        this.orderQueryService = orderQueryService;
    }

    @GetMapping("/{orderCode}/history")
    @Operation(summary = "테스트 for 본사의 주문 조회")
    public ResponseEntity<ResponseMessage<List<OrderStatusHistory>>> healthcheck(
            @PathVariable("orderCode") Integer orderCode
    ) {
        List<OrderStatusHistory> orderHistoryList = orderQueryService.getOrderHistoryByOrderId(orderCode);
        return ResponseEntity.ok(new ResponseMessage<>(200, "OK", orderHistoryList));
    }

    @GetMapping("/list")
    @Operation(summary = "본사의 주문 리스트 조회")
    public ResponseEntity<ResponseMessage<Page<HQOrderDTO>>> getOrderList(
            @PageableDefault(size = 10, page = 0) Pageable pageable,
            @RequestParam(name = "filter", required = false) String filter,
            @RequestParam(name = "sort", required = false) String sort,
                @RequestParam(name = "startDate", required = false) String startDate,
            @RequestParam(name = "endDate", required = false) String endDate
    ) {
        log.debug("startDate: {}", startDate);
        log.debug("endDate: {}", endDate);
        Page<HQOrderDTO> orderDTOList = orderQueryService.getOrderListForHQ(
                pageable,
                filter,
                sort,
                startDate,
                endDate
        );
        return ResponseEntity.ok(new ResponseMessage<>(200, "OK", orderDTOList));
    }

    @GetMapping("/excel")
    @Operation(summary = "엑셀 데이터 추출을 위한 주문 리스트 조회")
    public ResponseEntity<ResponseMessage<List<OrderResponseDTO>>> getOrderListExcel() {

        // TODO: 엑셀 다운로드를 위한 데이터 전달 API
        return ResponseEntity.ok(new ResponseMessage<>(200, "OK", null));
    }

    @GetMapping("/search")
    @Operation(summary = "주문 일자(기간) 별로 검색 타입(주문번호, 주문지점, 주문담당자)에 따른 검색")
    public ResponseEntity<ResponseMessage<Page<HQOrderDTO>>> getOrderListByHqSearch(
            @PageableDefault(size = 10, page = 0) Pageable pageable,
            @RequestParam(name = "filter", required = false) String filter,
            @RequestParam(name = "criteria", required = false) String criteria
    ) {
        Page<HQOrderDTO> orderDTOList = orderQueryService.searchOrderListForHQ(pageable, filter, criteria);
        return ResponseEntity.ok(new ResponseMessage<>(200, "OK", orderDTOList));
    }

    @GetMapping("/detail/{orderCode}")
    @Operation(summary = "주문 코드를 path variable로 활용한 주문 상세 조회")
    public ResponseEntity<ResponseMessage<OrderResponseDTO>> getOrderInformation(
            @PathVariable("orderCode") Integer orderCode) {
//        OrderResponseDTO orderResponseVO = orderService.getOrderDetailByHqWith(Integer.parseInt(orderCode));
        return ResponseEntity.ok(new ResponseMessage<>(200, "OK", null));
    }

    @GetMapping("/detail/{orderCode}/print/order-request")
    @Operation(summary = "주문 코드를 path variable로 활용한 주문요청서 출력데이터 조회")
    public ResponseEntity<ResponseMessage<OrderRequestDTO>> printOrderRequest(
            @PathVariable("orderCode") Integer orderCode) {
        OrderRequestDTO printedRequestedOrder = orderQueryService.printOrderRequest(orderCode);
        return ResponseEntity.ok(new ResponseMessage<>(200, "OK", printedRequestedOrder));
    }
}
