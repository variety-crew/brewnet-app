package com.varc.brewnetapp.common.domain.drafter;

import lombok.Getter;

@Getter
public enum DrafterApproved {
    APPROVE("APPROVE")
    , REJECT("REJECT")
    , NONE("NONE")
    ;
    private final String value;

    DrafterApproved(String value) {
        this.value = value;
    }
}
