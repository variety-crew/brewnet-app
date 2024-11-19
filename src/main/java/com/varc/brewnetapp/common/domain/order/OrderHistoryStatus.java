package com.varc.brewnetapp.common.domain.order;

import lombok.Getter;

@Getter
public enum OrderHistoryStatus {
    REQUESTED("REQUESTED"),
    CANCELED("CANCELED"),
    REJECTED("REJECTED"),
    SHIPPING("SHIPPING"),
    SHIPPED("SHIPPED")
    ;

    private final String status;

    OrderHistoryStatus(String status) {
        this.status = status;
    }
}
