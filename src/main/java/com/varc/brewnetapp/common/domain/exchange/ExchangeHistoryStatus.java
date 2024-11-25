package com.varc.brewnetapp.common.domain.exchange;
public enum ExchangeHistoryStatus {
    TOTAL_INBOUND("전체입고"),
    TOTAL_DISPOSAL("전체폐기"),
    PARTIAL_INBOUND("부분입고");

    private String krName;

    ExchangeHistoryStatus(String krName) {
        this.krName = krName;
    }

    public String getKrName() {
        return krName;
    }
}