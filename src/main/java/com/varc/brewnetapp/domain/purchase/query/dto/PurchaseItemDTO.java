package com.varc.brewnetapp.domain.purchase.query.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PurchaseItemDTO {

    private int letterOfPurchaseCode;       // 구매품의서 코드
    private int itemCode;                   // 상품 코드
    private String itemName;                // 상품명
    private String itemUniqueCode;          // 상품 고유 코드
    private int purchasePrice;              // 상품 구매단가(공급가)
    private int quantity;                   // 발주 수량
    private int purchaseSum;                // 상품의 총 공급가(구매단가 * 수량)
    private int purchaseVat;                // 상품의 총 부가세(구매단가 * 수량 * 10%)
}
