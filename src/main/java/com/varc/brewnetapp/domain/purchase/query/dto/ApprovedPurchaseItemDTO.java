package com.varc.brewnetapp.domain.purchase.query.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ApprovedPurchaseItemDTO {

    private int purchaseCode;               // 구매품의서 코드
    private int itemCode;                   // 발주 상품 코드
    private int quantity;                   // 발주 수량
    private boolean storageConfirmed;       // 입고 처리 여부
    private String createdAt;               // 구매품의서 생성일시
    private String itemName;                // 발주 상품명
    private String itemUniqueCode;          // 발주 상품 고유코드
    private String correspondentName;       // 거래처명
    private String storageName;             // 창고명
}
