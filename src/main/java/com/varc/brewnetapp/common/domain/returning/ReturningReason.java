package com.varc.brewnetapp.common.domain.returning;

public enum ReturningReason {
    DAMAGED("파손"),
    DEFECTIVE("품질불량"),
    MIND_CHANGE("단순변심"),
    OTHER("기타");

    private String krName;

    ReturningReason(String krName) {
        this.krName = krName;
    }

    public String getKrName() {
        return krName;
    }
}
