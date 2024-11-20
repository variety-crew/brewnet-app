package com.varc.brewnetapp.common.domain.exchange;

public enum ExchangeStatus {
    REQUESTED("교환요청"),
    CANCELED("교환취소"),
    APPROVED("교환승인"),
    REJECTED("교환반려"),
    PICKING("수거중"),
    PICKED("수거완료"),
    SHIPPING("배송중"),
    SHIPPED("배송완료"),
    COMPLETED("교환완료");

    private String krName;

    ExchangeStatus(String krName) {
        this.krName = krName;
    }

    public String getKrName() {
        return krName;
    }
}