package com.varc.brewnetapp.domain.order.command.domain.aggregate.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderApprovalStatus {
    UNCONFIRMED("UNCONFIRMED"),
    APPROVED("APPROVED"),
    REJECTED("REJECTED");

    private final String status;
}