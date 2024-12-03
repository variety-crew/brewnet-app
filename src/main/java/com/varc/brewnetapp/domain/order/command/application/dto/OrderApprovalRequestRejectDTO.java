package com.varc.brewnetapp.domain.order.command.application.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderApprovalRequestRejectDTO {
    private String comment;
}
