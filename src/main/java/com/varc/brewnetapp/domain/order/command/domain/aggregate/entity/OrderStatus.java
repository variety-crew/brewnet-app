package com.varc.brewnetapp.domain.order.command.domain.aggregate.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatus {
    REQUESTED("REQUESTED"),
    CANCELLED("CANCELLED"),
    APPROVED("APPROVED"),
    REJECTED("REJECTED"),
    SHIPPING("SHIPPING"),
    SHIPPED("SHIPPED")
    ;

    private final String status;
}