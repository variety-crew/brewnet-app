package com.varc.brewnetapp.domain.order.command.domain.aggregate.entity.compositionkey;


import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Builder
@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemCode implements Serializable {
    private int orderCode;
    private int itemCode;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        OrderItemCode that = (OrderItemCode) object;
        return orderCode == that.orderCode && itemCode == that.itemCode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderCode, itemCode);
    }
}
