package com.varc.brewnetapp.common.domain.order;

import lombok.Getter;

@Getter
public enum OrderApprovalStatus {
    UNCONFIRMED("UNCONFIRMED")
    , CANCELED("CANCELED")
    , APPROVED("APPROVED")
    , REJECTED("REJECTED")
    ;

    private final String status;

    OrderApprovalStatus(String status) {
        this.status = status;
    }
}
