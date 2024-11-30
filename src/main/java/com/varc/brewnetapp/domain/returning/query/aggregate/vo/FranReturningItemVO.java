package com.varc.brewnetapp.domain.returning.query.aggregate.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class FranReturningItemVO {
    private int itemCode;           // 반품신청 시, 프론트에서 상품 코드를 보내줘야 하기 때문에 필요
    private String itemUniqueCode;  // 상품 고유 코드
    private String itemName;        // 상품명
    private int quantity;           // 교환수량
    private int sellingPrice;       // 가맹점 구매단가 (전체 주문금액/교환수량)
    //    private int sumPrice;
    private int partSumPrice;       // 전체 주문금액
}