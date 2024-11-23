package com.varc.brewnetapp.domain.purchase.command.application.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ChangeInStockToAvailableRequestDTO {

    private int letterOfPurchaseCode;       // 구매품의서 코드
    private int itemCode;                   // 발주한 상품 코드
}

