package com.varc.brewnetapp.domain.exchange.command.domain.aggregate.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Objects;

// 교환 완료 상품 상태(tbl_exchange_item_status) 복합키
@Data
@Embeddable
public class ExchangeItemStatusCode implements Serializable {

    @Column(name="exchange_stock_history_code")
    private int exchangeStockHistoryCode;   // 교환완료 내역 코드

    @Column(name="item_code")
    private int itemCode;                   // 상품코드

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ExchangeItemStatusCode that = (ExchangeItemStatusCode) object;
        return exchangeStockHistoryCode == that.exchangeStockHistoryCode && itemCode == that.itemCode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(exchangeStockHistoryCode, itemCode);
    }
}