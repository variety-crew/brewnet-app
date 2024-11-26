package com.varc.brewnetapp.common.domain.position;

public enum Position {
    STAFF("사원"),
    ASSISTANT_MANAGER("대리"),
    MANAGER("과장"),
    CEO("교환반려");

    private String krName;

    Position(String krName) {
        this.krName = krName;
    }

    public String getKrName() {
        return krName;
    }
}
