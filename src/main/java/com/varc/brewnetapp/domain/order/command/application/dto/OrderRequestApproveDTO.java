package com.varc.brewnetapp.domain.order.command.application.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderRequestApproveDTO {
    private String comment;
}
