package com.varc.brewnetapp.domain.exchange.enums;

public enum ExchangeDraftApproval {
    APPROVE("승인"),
    REJECT("반려"),
    NONE("미정");

    private String krName;

    ExchangeDraftApproval(String krName) {
        this.krName = krName;
    }

    public String getKrName() {
        return krName;
    }
}
