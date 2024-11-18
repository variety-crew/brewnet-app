package com.varc.brewnetapp.domain.exchange.command.domain.aggregate.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Objects;

// 교환 별 결재자들 복합키
@Data
@Embeddable
public class ExchangeApproverCode implements Serializable {

    @Column(name="member_code")
    private int memberCode;

    @Column(name="exchange_code")
    private int exchangeCode;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ExchangeApproverCode that = (ExchangeApproverCode) object;
        return memberCode == that.memberCode && exchangeCode == that.exchangeCode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberCode, exchangeCode);
    }
}