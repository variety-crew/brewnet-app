package com.varc.brewnetapp.domain.order.command.application.dto.orderrequest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class OrderItemDTO {
    private Integer itemCode;
    private Integer quantity;
}
