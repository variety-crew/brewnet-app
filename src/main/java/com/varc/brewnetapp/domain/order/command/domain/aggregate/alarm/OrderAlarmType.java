package com.varc.brewnetapp.domain.order.command.domain.aggregate.alarm;


public enum OrderAlarmType {
    ORDER_REQUESTED("ORDER_REQUESTED")

    , ORDER_APPLIED("ORDER_APPLIED")
    , ORDER_REJECTED("ORDER_REJECTED")
    ;

    private final String type;

    OrderAlarmType(String type) {
        this.type = type;
    }

    public String getAlarmType() {
        return type;
    }
}
