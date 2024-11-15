package com.varc.brewnetapp.domain.order.command.domain.aggregate.entity;

public enum ApprovalType {
    APPROVE("APPROVE"),
    REJECT("REJECT");

    private final String type;

    ApprovalType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
