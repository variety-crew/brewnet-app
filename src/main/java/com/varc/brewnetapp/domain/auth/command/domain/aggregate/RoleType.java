package com.varc.brewnetapp.domain.auth.command.domain.aggregate;

import lombok.Getter;

@Getter
public enum RoleType {
      MASTER("ROLE_MASTER", 1)
    , ADMIN("ROLE_ADMIN", 2)
    , COMPANY_HEAD("COMPANY_HEAD", 3)
    , COMPANY_STAFF("COMPANY_STAFF", 4)
    , FRANCHISE("FRANCHISE", 5)
    , DRIVER("DRIVER", 6);

    private final String type;
    private final int roleId;

    RoleType(String type, int roleId) {
        this.type = type;
        this.roleId = roleId;
    }
}
