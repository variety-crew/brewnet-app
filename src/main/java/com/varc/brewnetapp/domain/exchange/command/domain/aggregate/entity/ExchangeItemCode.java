package com.varc.brewnetapp.domain.exchange.command.domain.aggregate.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Objects;

// 교환 별 상품(tbl_exchange_item) 복합키
@Data
@Embeddable
public class ExchangeItemCode implements Serializable {

    @Column(name="exchange_code")
    private int exchangeCode;       // 교환코드

    @Column(name="item_code")
    private int itemCode;           // 상품코드

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ExchangeItemCode that = (ExchangeItemCode) object;
        return exchangeCode == that.exchangeCode && itemCode == that.itemCode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(exchangeCode, itemCode);
    }
}