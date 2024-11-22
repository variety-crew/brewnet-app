package com.varc.brewnetapp.common.domain.order;

import lombok.Getter;

@Getter
public enum OrderStatus {
    UNCONFIRMED("UNCONFIRMED")
    , CANCELED("CANCELED")
    , APPROVED("APPROVED")
    , REJECTED("REJECTED")
    ;

    private final String status;

    OrderStatus(String status) {
        this.status = status;
    }
}
