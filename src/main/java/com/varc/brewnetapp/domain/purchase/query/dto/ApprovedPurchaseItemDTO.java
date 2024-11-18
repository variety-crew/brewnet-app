package com.varc.brewnetapp.domain.purchase.query.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ApprovedPurchaseItemDTO {

    private int purchaseCode;
    private int itemCode;
    private int quantity;
    private String createdAt;
    private String itemName;
    private String itemUniqueCode;
    private String correspondentName;
    private String storageName;
}
