package com.varc.brewnetapp.domain.order.command.application.controller;

import com.varc.brewnetapp.common.ResponseMessage;
import com.varc.brewnetapp.domain.order.command.application.dto.orderrequest.OrderRequestDTO;
import com.varc.brewnetapp.domain.order.command.application.dto.orderrequest.OrderRequestResponseDTO;
import com.varc.brewnetapp.domain.order.command.application.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
