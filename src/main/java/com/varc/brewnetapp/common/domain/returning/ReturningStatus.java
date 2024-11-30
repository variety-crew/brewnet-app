package com.varc.brewnetapp.common.domain.returning;

public enum ReturningStatus {
    REQUESTED("반품요청"),
    PENDING("진행중"),
    CANCELED("반품취소"),
    APPROVED("반품승인"),
    REJECTED("반품반려"),
    PICKING("수거중"),
    PICKED("수거완료"),
    COMPLETED("반품완료");

    private String krName;

    ReturningStatus(String krName) {
        this.krName = krName;
    }

    public String getKrName() {
        return krName;
    }
}
