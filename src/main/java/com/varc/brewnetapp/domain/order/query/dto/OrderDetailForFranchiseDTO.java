package com.varc.brewnetapp.domain.order.query.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDetailForFranchiseDTO {
    private int orderCode;
    private String createdAt;
    private int sumPrice;
    private String doneDate;
    private String orderStatus;

    private List<OrderItem> orderItemList;
}
