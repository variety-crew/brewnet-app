package com.varc.brewnetapp.domain.order.query.controller;

import com.varc.brewnetapp.common.ResponseMessage;
import com.varc.brewnetapp.domain.order.query.service.OrderService;
import com.varc.brewnetapp.domain.order.query.vo.hq.response.OrderResponseVO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping
    @Operation(summary = "본사의 주문 리스트 조회")
    public ResponseEntity<ResponseMessage<List<OrderResponseVO>>> getOrderList() {
        List<OrderResponseVO> orderResponseVO = orderService.getAllOrderListByHqRequest();
        return ResponseEntity.ok(new ResponseMessage<>(200, "OK", orderResponseVO));
    }

    @GetMapping("/excel")
    @Operation(summary = "엑셀 데이터 추출을 위한 주문 리스트 조회")
    public ResponseEntity<ResponseMessage<List<OrderResponseVO>>> getOrderListExcel() {

        // TODO: 엑셀 다운로드를 위한 데이터 전달 API
        return ResponseEntity.ok(new ResponseMessage<>(200, "OK", null));
    }

    @GetMapping("/search")
    @Operation(summary = "주문 일자(기간) 별로 검색 타입(주문번호, 주문지점, 주문담당자)에 따른 검색")
    public ResponseEntity<ResponseMessage<List<OrderResponseVO>>> getOrderListByHqSearch() {
        return ResponseEntity.ok(new ResponseMessage<>(200, "OK", null));
    }

    @GetMapping("/{orderCode}")
    @Operation(summary = "주문 코드를 path variable로 활용한 주문 상세 조회")
    public ResponseEntity<ResponseMessage<OrderResponseVO>> getOrderInformation(
            @PathVariable("orderCode") String orderCode) {
        OrderResponseVO orderResponseVO = orderService.getOrderDetailByHqWith(Integer.parseInt(orderCode));
        return ResponseEntity.ok(new ResponseMessage<>(200, "OK", orderResponseVO));
    }
}