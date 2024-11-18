package com.varc.brewnetapp.domain.order.command.domain.aggregate.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderHistoryStatus {
    REQUESTED("REQUESTED"),
    CANCELED("CANCELLED"),
    APPROVED("APPROVED"),
    REJECTED("REJECTED"),
    SHIPPING("SHIPPING"),
    SHIPPED("SHIPPED")
    ;

    private final String status;
}
