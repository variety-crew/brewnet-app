package com.varc.brewnetapp.domain.purchase.command.application.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PurchaseApprovalRequestDTO {

    private String comment;     // 결재자 첨언
}
