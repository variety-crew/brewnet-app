package com.varc.brewnetapp.common.domain.returning;
public enum ReturningRefundStatus {
    TOTAL_REFUND("전체환불"),
    PARTIAL_REFUND("부분환불"),
    NON_REFUNDABLE("환불불가");

    private String krName;

    ReturningRefundStatus(String krName) {
        this.krName = krName;
    }

    public String getKrName() {
        return krName;
    }
}
