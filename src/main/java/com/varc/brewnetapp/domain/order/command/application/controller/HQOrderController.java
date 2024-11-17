package com.varc.brewnetapp.domain.order.command.application.controller;

import com.varc.brewnetapp.domain.order.command.application.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController(value="commandHQOrderController")
@RequestMapping("api/v1/hq/orders")
public class HQOrderController {
    private final OrderService orderService;

    @Autowired
    public HQOrderController(OrderService orderService) {
        this.orderService = orderService;
    }
}
