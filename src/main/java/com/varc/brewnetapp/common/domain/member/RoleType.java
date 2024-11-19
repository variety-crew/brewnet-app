package com.varc.brewnetapp.common.domain.member;

public enum RoleType {
    ROLE_MASTER("ROLE_MASTER", 1)
    , ROLE_GENERAL_ADMIN("ROLE_GENERAL_ADMIN", 2)
    , ROLE_RESPONSIBLE_ADMIN("ROLE_RESPONSIBLE_ADMIN", 3)
    , ROLE_FRANCHISE("ROLE_FRANCHISE", 4)
    , ROLE_DELIVERY("ROLE_DELIVERY", 5);

    private final String type;
    private final int roleId;

    RoleType(String type, int roleId) {
        this.type = type;
        this.roleId = roleId;
    }
}
