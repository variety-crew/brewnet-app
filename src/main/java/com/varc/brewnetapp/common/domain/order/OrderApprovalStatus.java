package com.varc.brewnetapp.common.domain.order;


public enum OrderApprovalStatus {
    UNCONFIRMED("UNCONFIRMED")
    , CANCELED("CANCELED")
    , APPROVED("APPROVED")
    , REJECTED("REJECTED")
    ;

    private final String value;

    OrderApprovalStatus(String value) {
        this.value = value;
    }

    public String getStatus() {
        return value;
    }
}
