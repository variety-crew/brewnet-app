package com.varc.brewnetapp.domain.exchange.command.domain.aggregate.vo;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ExchangeReqItemVO {
    private int itemCode;
//    private String itemUniqueCode;  // 상품 고유 코드(품목코드)
//    private String itemName;        // 상품명
    private int quantity;           // 교환수량
//    private int sellingPrice;       // 가맹점 구매단가
//    private int sumPrice;           // 주문금액 (quantity * sellingPrice)
}
