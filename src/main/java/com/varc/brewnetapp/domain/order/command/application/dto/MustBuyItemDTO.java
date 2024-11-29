package com.varc.brewnetapp.domain.order.command.application.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MustBuyItemDTO {
    private int itemCode;
    private int quantity;
}
