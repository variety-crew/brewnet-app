package com.varc.brewnetapp.domain.order.query.dto;


import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FranchiseOrderDTO {
    private int orderCode;
    private String createdAt;
    private int sumPrice;

    private List<OrderItem> orderItemList;
    private List<OrderStatusHistory> orderStatusHistoryList;
}
