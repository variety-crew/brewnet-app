package com.varc.brewnetapp.domain.purchase.command.application.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PurchasePrintItemDTO {

    private String itemName;            // 발주 품목명
    private String itemUniqueCode;      // 발주 품목 고유코드
    private int purchasePrice;          // 발주 품목 가격(공급가)
    private int vatPrice;               // 발주 품목 부가세(공급가 / 10)
    private int quantity;               // 발주 품목 수량
}
