package com.varc.brewnetapp.domain.exchange.enums;

public enum ExchangeReason {
    DAMAGED("파손"),
    DEFECTIVE("품질불량"),
    OTHER("기타");

    private String krName;

    ExchangeReason(String krName) {
        this.krName = krName;
    }

    public String getKrName() {
        return krName;
    }
}
