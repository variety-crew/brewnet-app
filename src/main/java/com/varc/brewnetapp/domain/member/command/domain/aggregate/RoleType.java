package com.varc.brewnetapp.domain.member.command.domain.aggregate;

import lombok.Getter;

@Getter
public enum RoleType {
    MASTER("ROLE_MASTER", 1)
    , GENERAL_ADMIN("ROLE_GENERAL_ADMIN", 2)
    , RESPONSIBLE_ADMIN("ROLE_RESPONSIBLE_ADMIN", 3)
    , FRANCHISE("ROLE_FRANCHISE", 4)
    , DELIVERY("ROLE_DELIVERY", 5);

    private final String type;
    private final int roleId;

    RoleType(String type, int roleId) {
        this.type = type;
        this.roleId = roleId;
    }
}
