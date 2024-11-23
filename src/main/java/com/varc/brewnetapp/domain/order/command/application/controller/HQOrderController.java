package com.varc.brewnetapp.domain.order.command.application.controller;

import com.varc.brewnetapp.common.ResponseMessage;
import com.varc.brewnetapp.domain.order.command.application.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/v1/hq/orders")
public class HQOrderController {
    private final OrderService orderService;

    @Autowired
    public HQOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/reject/request/{orderCode}")
    public ResponseEntity<ResponseMessage<Object>> drafterReject(@PathVariable("orderCode") String orderCode) {

//        orderService.rejectOrderByDrafter(orderCode);

        return ResponseEntity.ok(
                new ResponseMessage<>(200, "order request successfully rejected", null)
        );
    }
}
