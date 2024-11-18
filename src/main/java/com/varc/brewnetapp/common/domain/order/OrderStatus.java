package com.varc.brewnetapp.common.domain.order;

import lombok.Getter;

@Getter
public enum OrderStatus {
    APPROVED("APPROVED")
    , UNCONFIRMED("UNCONFIRMED")
    , REJECTED("REJECTED")
    ;

    private final String value;

    OrderStatus(String value) {
        this.value = value;
    }
}
