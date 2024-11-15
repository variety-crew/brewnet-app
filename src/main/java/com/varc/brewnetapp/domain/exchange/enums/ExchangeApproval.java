package com.varc.brewnetapp.domain.exchange.enums;

public enum ExchangeApproval {
    APPROVED("승인"),
    CANCELED("취소"),
    UNCONFIRMED("미확인"),
    REJECTED("반려");

    private String krName;

    ExchangeApproval(String krName) {
        this.krName = krName;
    }

    public String getKrName() {
        return krName;
    }
}
