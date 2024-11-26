package com.varc.brewnetapp.domain.correspondent.query.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CorrespondentItemDTO {

    private int correspondentCode;          // 거래처 코드
    private int itemCode;                   // 상품 코드
    private String categoryName;            // 상품의 카테고리명
    private String itemUniqueCode;          // 상품 고유코드
    private String itemName;                // 상품명
    private String correspondentName;       // 거래처명
    private int sellingPrice;               // 판매단가
    private int purchasePrice;              // 구매단가
    private int safetyStock;                // 안전재고값
    private boolean active;                 // 거래처 상품의 활성화여부
}
