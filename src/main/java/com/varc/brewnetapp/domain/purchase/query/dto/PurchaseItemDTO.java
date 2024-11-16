package com.varc.brewnetapp.domain.purchase.query.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PurchaseItemDTO {

    private int letterOfPurchaseCode;
    private int itemCode;
    private String itemName;
    private String itemUniqueCode;
    private int purchasePrice;
    private int quantity;
}
