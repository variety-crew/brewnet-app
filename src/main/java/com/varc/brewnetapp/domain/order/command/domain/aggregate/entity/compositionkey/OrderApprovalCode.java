package com.varc.brewnetapp.domain.order.command.domain.aggregate.entity.compositionkey;


import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Builder
@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderApprovalCode {
    private int memberCode;
    private int orderCode;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        OrderApprovalCode that = (OrderApprovalCode) object;
        return memberCode == that.memberCode && orderCode == that.orderCode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberCode, orderCode);
    }
}
