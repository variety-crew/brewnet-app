package com.varc.brewnetapp.common.domain.approve;

public enum Approval {
    APPROVED("승인"),
    CANCELED("취소"),
    UNCONFIRMED("미확인"),
    REJECTED("반려");

    private String krName;

    Approval(String krName) {
        this.krName = krName;
    }

    public String getKrName() {
        return krName;
    }
}
