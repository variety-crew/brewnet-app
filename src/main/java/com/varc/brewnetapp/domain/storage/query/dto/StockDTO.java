package com.varc.brewnetapp.domain.storage.query.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class StockDTO {

    private int storageCode;            // 창고 코드
    private int itemCode;               // 상품 코드
    private int availableStock;         // 가용재고
    private int outStock;               // 출고예정재고
    private int inStock;                // 입고예정재고
    private String itemUniqueCode;      // 상품 고유코드
    private String itemName;            // 상품명
    private int safetyStock;            // 안전재고값
    private int currentStock;           // 현재재고(가용재고 + 출고예정재고)
}
