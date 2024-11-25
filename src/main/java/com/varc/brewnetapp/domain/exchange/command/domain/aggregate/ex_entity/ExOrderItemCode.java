package com.varc.brewnetapp.domain.exchange.command.domain.aggregate.ex_entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
@Builder(toBuilder = true)
@EqualsAndHashCode
public class ExOrderItemCode implements Serializable {
    @Column(name = "order_code")
    private int orderCode;

    @Column(name = "item_code")
    private int itemCode;
}