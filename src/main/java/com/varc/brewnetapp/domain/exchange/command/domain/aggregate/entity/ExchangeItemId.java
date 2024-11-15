package com.varc.brewnetapp.domain.exchange.command.domain.aggregate.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

// 교환 별 상품(tbl_exchange_item) 복합키
@Data
@Embeddable
public class ExchangeItemId implements Serializable {

    @Column(name="exchange_code")
    private int exchangeCode;       // 교환코드

    @Column(name="item_code")
    private int itemCode;           // 상품코드
}