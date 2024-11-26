package com.varc.brewnetapp.domain.storage.command.application.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ChangeStockRequestDTO {

    private int storageCode;    // 선택한 창고 코드
    private int itemCode;       // 재고 조정할 상품 코드
    private int quantity;       // 가용재고에 합산할 수량
}
