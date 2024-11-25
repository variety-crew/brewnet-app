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
@EqualsAndHashCode
public class ExchangeItemStatusCode implements Serializable {

    @Column(name="exchange_stock_history_code")
    private int exchangeStockHistoryCode;   // 교환완료 내역 코드

    @Column(name="item_code")
    private int itemCode;                   // 상품코드
}