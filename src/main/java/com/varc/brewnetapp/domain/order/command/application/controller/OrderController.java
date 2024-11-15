package com.varc.brewnetapp.domain.order.command.application.controller;

import com.varc.brewnetapp.common.ResponseMessage;
import com.varc.brewnetapp.domain.order.command.application.dto.orderrequest.OrderRequestDTO;
import com.varc.brewnetapp.domain.order.command.application.dto.orderrequest.OrderRequestResponseDTO;
import com.varc.brewnetapp.domain.order.command.application.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController(value="commandOrderController")
@RequestMapping("api/v1/order")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // 가맹점이 주문 요청
    @PostMapping
    public ResponseEntity<ResponseMessage<OrderRequestResponseDTO>> franchiseOrder(
            @RequestAttribute("loginId") String loginId,
            @RequestBody OrderRequestDTO orderRequestDTO
    ) {
        OrderRequestResponseDTO orderRequestResponse = orderService.orderRequestByFranchise(orderRequestDTO);
        return ResponseEntity.ok(
                new ResponseMessage<>(200, "본사로의 주문요청이 완료됐습니다.", orderRequestResponse)
        );
    }

    @DeleteMapping("/{orderCode}")
    public ResponseEntity<ResponseMessage<Void>> cancelOrder(
            @PathVariable("orderCode") String orderCode
    ) {
        log.debug("cancelOrder called");
        orderService.cancelOrderRequest(Integer.parseInt(orderCode));
        return ResponseEntity.ok(
                new ResponseMessage<>(204, "주문 요청이 취소되었습니다.", null)
        );
    }
}
