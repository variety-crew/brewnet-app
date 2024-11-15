package com.varc.brewnetapp.domain.order.command.application.dto.orderrequest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class OrderRequestDTO {
    private int franchiseCode;
    private List<OrderItemDTO> orderList;
}
