package com.varc.brewnetapp.domain.order.command.domain.aggregate.entity;

public enum OrderApprovalStatus {
    UNCONFIRMED("UNCONFIRMED"),
    APPROVED("APPROVED"),
    REJECTED("REJECTED");

    private final String status;

    OrderApprovalStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
