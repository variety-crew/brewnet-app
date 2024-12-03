package com.varc.brewnetapp.domain.returning.command.domain.aggregate.entity;

import com.varc.brewnetapp.domain.exchange.command.domain.aggregate.entity.ExchangeItemStatusCode;
import com.varc.brewnetapp.domain.returning.command.domain.aggregate.entity.compositionkey.ReturningItemStatusCode;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Builder(toBuilder = true)
@Getter
//@Setter
@Entity
@Table(name = "tbl_return_item_status")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class ReturningItemStatus {
    @EmbeddedId
    private ReturningItemStatusCode returningItemStatusCode;  // (복합키) 반품완료 내역 코드, 상품코드

    @Column(name = "quantity", nullable = false)
    private int quantity;               // 총수량

    @Column(name = "restock_quantity", nullable = false)
    private int restock_quantity;       // 재입고수량
}