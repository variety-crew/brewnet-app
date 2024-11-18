package com.varc.brewnetapp.domain.purchase.common;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SearchPurchaseCriteria {

    private Integer purchaseCode;           // 발주코드
    private String memberName;              // 기안자
    private String correspondentName;       // 거래처명
    private String storageName;             // 창고명
    private String startDate;               // 시작일(언제부터)
    private String endDate;                 // 종료일(언제까지)

    private int pageNumber;                 // 현재 페이지 번호
    private int pageSize;                   // 페이지 크기
    private int offset;                     // (현재 페이지 번호 - 1) * 페이지 크기
}
