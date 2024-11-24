package com.varc.brewnetapp.domain.order.command.application.controller;

import com.varc.brewnetapp.common.ResponseMessage;
import com.varc.brewnetapp.domain.order.command.application.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/v1/super/orders")
public class HQSuperController {
    private final OrderService orderService;

    @Autowired
    public HQSuperController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/approve/{orderCode}")
    public ResponseEntity<ResponseMessage<Object>> approveOrderRequest(
            @PathVariable String orderCode,
            @RequestAttribute String loginId
    ) {
//        orderService.approveRequestedOrder(orderCode, loginId);
        return ResponseEntity.ok(
                new ResponseMessage<>(200, "successfully approved order request", null)
        );
    }
}
