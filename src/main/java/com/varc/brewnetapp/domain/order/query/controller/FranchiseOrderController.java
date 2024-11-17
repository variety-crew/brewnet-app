package com.varc.brewnetapp.domain.order.query.controller;

import com.varc.brewnetapp.domain.order.command.application.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "queryOrderController")
@RequestMapping("api/v1/franchise/orders")
public class FranchiseOrderController {

    private final OrderService orderService;

    @Autowired
    public FranchiseOrderController(OrderService orderService) {
        this.orderService = orderService;
    }
}
