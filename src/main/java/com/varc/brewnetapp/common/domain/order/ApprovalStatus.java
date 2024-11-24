package com.varc.brewnetapp.common.domain.order;

public enum ApprovalStatus {
    UNCONFIRMED("UNCONFIRMED")
    , APPROVED("APPROVED")
    , REJECTED("REJECTED")
    ;

    private final String value;

    public String getValue() {
        return value;
    }

    ApprovalStatus(String value) {
        this.value = value;
    }
}
