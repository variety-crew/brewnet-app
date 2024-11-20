package com.varc.brewnetapp.domain.exchange.command.domain.aggregate.ex_entity;

import com.varc.brewnetapp.common.domain.exchange.Availability;
import com.varc.brewnetapp.domain.order.command.domain.aggregate.entity.compositionkey.OrderItemCode;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
@Builder(toBuilder = true)
@Getter
@Entity
@Table(name = "tbl_order_item")
//@Entity(name = "tbl_exchange_order_item")
public class ExOrderItem {
    @EmbeddedId
    private ExOrderItemCode orderItemCode;

    @Column(name = "quantity")
    private int quantity;

    @Enumerated(EnumType.STRING)
    @Column(name = "available", nullable = false)
    private Availability available;         // 반품/교환요청 가능여부

    @Column(name = "part_sum_price", nullable = false)
    private Integer partSumPrice;           // 주문 품목별 총액
}