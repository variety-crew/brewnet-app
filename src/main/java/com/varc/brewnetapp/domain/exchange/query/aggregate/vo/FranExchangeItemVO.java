package com.varc.brewnetapp.domain.exchange.query.aggregate.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class FranExchangeItemVO {
    private String itemUniqueCode;  // 상품 고유 코드
    private String itemName;        // 상품명
    private int quantity;           // 교환수량
    private int sellingPrice;       // 가맹점 구매단가 (전체 주문금액/교환수량)
//    private int sumPrice;
    private int partSumPrice;       // 전체 주문금액
}
