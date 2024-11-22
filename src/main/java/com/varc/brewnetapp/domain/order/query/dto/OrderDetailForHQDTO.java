package com.varc.brewnetapp.domain.order.query.dto;


import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDetailForHQDTO {
    private int orderCode;
    private String createdAt;
    private String franchiseName;
    private String comment;
    private String managerName;
    private int sumPrice;

    private List<OrderItem> orderItemList;
}
