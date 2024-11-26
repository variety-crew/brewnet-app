package com.varc.brewnetapp.domain.order.command.application.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DrafterRejectOrderRequestDTO {
    private String reason;
}
