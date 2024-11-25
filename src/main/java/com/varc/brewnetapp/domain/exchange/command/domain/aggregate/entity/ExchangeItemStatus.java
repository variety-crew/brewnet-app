package com.varc.brewnetapp.domain.exchange.command.domain.aggregate.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Builder(toBuilder = true)
@Getter
//@Setter
@Entity
@Table(name = "tbl_exchange_item_status")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class ExchangeItemStatus {
    @EmbeddedId
    private ExchangeItemStatusCode exchangeItemStatusCode;  // (복합키) 교환완료 내역 코드, 상품코드

    @Column(name = "quantity", nullable = false)
    private int quantity;               // 총수량

    @Column(name = "restock_quantity", nullable = false)
    private int restock_quantity;       // 총수량
}
