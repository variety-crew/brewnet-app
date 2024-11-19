package com.varc.brewnetapp.domain.purchase.command.application.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PurchaseApprovalRequestDTO {

    private int approverCode;
    private String comment;
}
