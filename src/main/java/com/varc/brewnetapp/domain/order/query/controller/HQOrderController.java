package com.varc.brewnetapp.domain.order.query.controller;

import com.varc.brewnetapp.common.ResponseMessage;
import com.varc.brewnetapp.common.domain.member.RoleType;

import com.varc.brewnetapp.domain.order.query.dto.OrderDTO;
import com.varc.brewnetapp.domain.order.query.dto.OrderResponseDTO;
import com.varc.brewnetapp.domain.order.query.service.OrderService;
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
@RestController(value="queryHQOrderController")
@RequestMapping("api/v1/hq/orders")
public class HQOrderController {
    private final OrderService orderService;

    @Autowired
    public HQOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("health")
    @Operation(summary = "테스트 for 본사의 주문 조회")
    public ResponseEntity<ResponseMessage<Page<OrderDTO>>> healthcheck(
            @PageableDefault(size = 10, page = 0) Pageable pageable,
            @RequestParam(name = "filter", required = false) String filter,
            @RequestParam(name = "sort", required = false) String sort,
            @RequestAttribute(name = "loginId") String loginId
    ) {
        Page<OrderDTO> orderDTOList = orderService.getOrderListForHQ(
                pageable,
                filter,
                sort
        );
        return ResponseEntity.ok(new ResponseMessage<>(200, "OK", orderDTOList));
    }

    @GetMapping
    @Operation(summary = "본사의 주문 리스트 조회")
    public ResponseEntity<ResponseMessage<Page<OrderDTO>>> getOrderList(
            @PageableDefault(size = 10, page = 0) Pageable pageable,
            @RequestParam(name = "filter", required = false) String filter,
            @RequestParam(name = "sort", required = false) String sort
    ) {
        Page<OrderDTO> orderDTOList = orderService.getOrderListForHQ(
                pageable,
                filter,
                sort
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
    public ResponseEntity<ResponseMessage<List<OrderResponseDTO>>> getOrderListByHqSearch() {
        return ResponseEntity.ok(new ResponseMessage<>(200, "OK", null));
    }

    @GetMapping("/{orderCode}")
    @Operation(summary = "주문 코드를 path variable로 활용한 주문 상세 조회")
    public ResponseEntity<ResponseMessage<OrderResponseDTO>> getOrderInformation(
            @PathVariable("orderCode") String orderCode) {
//        OrderResponseDTO orderResponseVO = orderService.getOrderDetailByHqWith(Integer.parseInt(orderCode));
        return ResponseEntity.ok(new ResponseMessage<>(200, "OK", null));
    }
}
