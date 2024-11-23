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

    @PostMapping("/request/{orderCode}/reject")
    public ResponseEntity<ResponseMessage<Object>> drafterReject(
            @PathVariable("orderCode") Integer orderCode,
            @RequestAttribute("loginId") String loginId
    ) {

        orderService.rejectOrderByDrafter(orderCode, loginId);

        return ResponseEntity.ok(
                new ResponseMessage<>(200, "order request successfully rejected", null)
        );
    }
}
