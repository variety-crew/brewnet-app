package com.varc.brewnetapp.domain.purchase.command.application.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PurchaseItemDTO {

    private int itemCode;
    private int quantity;
    private int totalPrice;
}
