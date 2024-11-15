package com.varc.brewnetapp.domain.exchange.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Getter
@Setter
@Entity
@Table(name = "tbl_exchange_item")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ExchangeItem {
    @EmbeddedId
    private ExchangeItemId exchangeItemId;  // (복합키) 교환코드, 상품코드

    @Column(name = "quantity", nullable = false)
    private int quantity;                   // 수량
}
