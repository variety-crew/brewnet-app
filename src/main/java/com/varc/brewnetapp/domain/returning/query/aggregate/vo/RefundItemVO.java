package com.varc.brewnetapp.domain.returning.query.aggregate.vo;

public class RefundItemVO {
    private String itemUniqueCode;  // 상품 고유 코드
    private String itemName;        // 상품명
    private String superCategory;   // 상위 카테고리
    private String subCategory;     // 하위 카테고리
    private int quantity;           // 전체수량
    private int refundPrice;        // 환불금액
}
