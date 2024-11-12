package com.varc.brewnetapp.domain.member.command.domain.aggregate;

import java.io.Serializable;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class MemberRolePK implements Serializable {
    private int memberCode;
    private int roleCode;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        MemberRolePK that = (MemberRolePK) object;
        return memberCode == that.memberCode && roleCode == that.roleCode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberCode, roleCode);
    }
}
