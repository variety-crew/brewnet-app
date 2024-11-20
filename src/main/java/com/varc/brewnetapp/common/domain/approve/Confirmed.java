package com.varc.brewnetapp.common.domain.approve;

// 타부서 교환 내역 확인 - 내역 확인 여부 ENUM
public enum Confirmed {
    UNREAD("안읽음"),
    READ("읽음"),
    CONFIRMED("처리완료");

    private String krName;

    Confirmed(String krName) {
        this.krName = krName;
    }

    public String getKrName() {
        return krName;
    }
}
