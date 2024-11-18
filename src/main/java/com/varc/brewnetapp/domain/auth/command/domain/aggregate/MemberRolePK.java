package com.varc.brewnetapp.domain.auth.command.domain.aggregate;

import java.io.Serializable;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class MemberRolePK implements Serializable {
    private int memberCode;
    private int roleCode;

}
