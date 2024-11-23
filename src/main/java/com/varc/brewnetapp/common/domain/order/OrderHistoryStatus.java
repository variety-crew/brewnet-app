package com.varc.brewnetapp.common.domain.order;

import lombok.Getter;

@Getter
public enum OrderHistoryStatus {
    REQUESTED("REQUESTED"),
    PENDING("PENDING"),
    CANCELED("CANCELED"),
    APPROVED("APPROVED"),
    REJECTED("REJECTED"),
    SHIPPING("SHIPPING"),
    SHIPPED("SHIPPED")
    ;

    private final String value;

    OrderHistoryStatus(String value) {
        this.value = value;
    }
}
