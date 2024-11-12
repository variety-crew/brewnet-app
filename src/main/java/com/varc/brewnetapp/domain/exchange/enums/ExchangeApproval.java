package com.varc.brewnetapp.domain.exchange.enums;

public enum ExchangeApproval {
    APPROVAL("승인"),
    UNCONFIRM("미확인"),
    RETURN("반려");

    private String krName;

    ExchangeApproval(String krName) {
        this.krName = krName;
    }

    public String getKrName() {
        return krName;
    }
}
