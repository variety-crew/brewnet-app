package com.varc.brewnetapp.domain.order.query.dto;

import com.varc.brewnetapp.common.domain.drafter.DrafterApproved;
import com.varc.brewnetapp.common.domain.order.OrderStatus;
import lombok.*;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDTO {
    private int orderCode;
    private String comment; // written by drafter
    private String createdAt;
    private boolean active;
    private OrderStatus approvalStatus;
    private DrafterApproved drafterApproved;
    private int sumPrice;

    private OrderFranchiseDTO orderFranchiseDTO;
    private List<OrderItemDTO> orderItems;
    private List<OrderStatusHistoryDTO> orderStatusHistoryDTOList;
}
