package com.varc.brewnetapp.domain.order.query.dto;

import com.varc.brewnetapp.common.domain.drafter.DrafterApproved;
import com.varc.brewnetapp.common.domain.order.OrderStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDTO {
    private int orderCode;
    private String comment; // written by drafter
    private LocalDateTime createdAt;
    private boolean active;
    private OrderStatus approved;
    private DrafterApproved drafterApproved;
    private int sumPrice;
}
