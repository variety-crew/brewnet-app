package com.varc.brewnetapp.domain.correspondent.common;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SearchCorrespondentItemCriteria {

    private Integer correspondentCode;      // 거래처 코드
    private String itemUniqueCode;          // 상품 고유코드
    private String itemName;                // 상품명

    private int pageNumber;                 // 현재 페이지 번호
    private int pageSize;                   // 페이지 크기
    private int offset;                     // (현재 페이지 번호 - 1) * 페이지 크기
}
