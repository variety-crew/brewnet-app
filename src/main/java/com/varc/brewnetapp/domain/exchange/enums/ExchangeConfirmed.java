package com.varc.brewnetapp.domain.exchange.enums;

// 타부서 교환 내역 확인 - 내역 확인 여부 ENUM
public enum ExchangeConfirmed {
    UNREAD("안읽음"),
    READ("읽음"),
    CONFIRM("처리완료");

    private String krName;

    ExchangeConfirmed(String krName) {
        this.krName = krName;
    }

    public String getKrName() {
        return krName;
    }
}
