package com.varc.brewnetapp.domain.order.query.dto;

import com.varc.brewnetapp.common.domain.order.OrderHistoryStatus;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class OrderStatusHistory {
    private int orderStatusHistoryCode;
    private String orderHistoryStatus;
    private String createdAt;
}
