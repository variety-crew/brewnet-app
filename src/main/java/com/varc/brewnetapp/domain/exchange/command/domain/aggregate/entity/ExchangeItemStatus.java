package com.varc.brewnetapp.domain.exchange.command.domain.aggregate.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Getter
//@Setter
@Entity
@Table(name = "tbl_exchange_item_status")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ExchangeItemStatus {
    @EmbeddedId
    private ExchangeItemStatusCode exchangeItemStatusId;  // (복합키) 교환완료 내역 코드, 상품코드

    @Column(name = "quantity", nullable = false)
    private int quantity;               // 총수량

    @Column(name = "restock_quantity", nullable = false)
    private int restock_quantity;       // 총수량
}
