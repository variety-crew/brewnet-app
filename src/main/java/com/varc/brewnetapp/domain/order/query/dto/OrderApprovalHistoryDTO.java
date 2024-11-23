package com.varc.brewnetapp.domain.order.query.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderApprovalHistoryDTO {
    private int orderCode;
    private String approved;
    private String createdAt;
    private String comment;

    private String approverName;
    private String position;
}
