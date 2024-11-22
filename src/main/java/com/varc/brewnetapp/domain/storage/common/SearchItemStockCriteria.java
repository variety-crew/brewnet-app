package com.varc.brewnetapp.domain.storage.common;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SearchItemStockCriteria {

    private Integer storageCode;            // 창고 코드
    private String itemName;                // 상품명

    private int pageNumber;                 // 현재 페이지 번호
    private int pageSize;                   // 페이지 크기
    private int offset;                     // (현재 페이지 번호 - 1) * 페이지 크기
}
