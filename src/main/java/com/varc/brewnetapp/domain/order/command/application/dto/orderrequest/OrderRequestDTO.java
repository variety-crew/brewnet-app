package com.varc.brewnetapp.domain.order.command.application.dto.orderrequest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderRequestDTO {
    private List<OrderItem> orderList;
}
