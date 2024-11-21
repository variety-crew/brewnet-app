package com.varc.brewnetapp.domain.order.query.dto;

import com.varc.brewnetapp.common.domain.drafter.DrafterApproved;
import com.varc.brewnetapp.common.domain.order.OrderApprovalStatus;
import lombok.*;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDTO {
    private int orderCode;
    private String createdAt;
    private boolean active;
    private OrderApprovalStatus approvalStatus;
    private DrafterApproved drafterApproved;
    private int sumPrice;

    private OrderFranchise orderFranchise;
    private List<OrderItem> orderItemList;
    private List<OrderStatusHistory> orderStatusHistoryList;
}
